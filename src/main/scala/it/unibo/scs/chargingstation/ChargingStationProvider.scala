package it.unibo.scs.chargingstation

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors
import it.unibo.scs.chargingstation.ChargingStationActor.ChargingStationServiceKey

object ChargingStationProvider:
  private type ChargingStationRegistry = Map[ActorRef[ChargingStationEvents.Request], ChargingStation]
  sealed trait Request
  case class GetChargingStations(replyTo: ActorRef[Set[ChargingStation]]) extends Request
  case class UpdateChargingStation(chargingStation: ChargingStation, ref: ActorRef[ChargingStationEvents.Request]) extends Request
  private case class ChargingStationsUpdated(chargingStations: Set[ActorRef[ChargingStationEvents.Request]]) extends Request
  private case class BadRequest() extends Request


  val ProviderServiceKey: ServiceKey[Request] = ServiceKey("ChargingStationProvider")

  def apply(chargingStations: ChargingStationRegistry = Map.empty): Behavior[Request] =
    Behaviors setup { ctx =>
      val subscriptionAdapter = ctx.messageAdapter[Receptionist.Listing]{
        case ChargingStationServiceKey.Listing(refs) =>
          ChargingStationsUpdated(refs)
      }
      ctx.system.receptionist ! Receptionist.Subscribe(ChargingStationServiceKey, subscriptionAdapter)

      val csAdapter = ctx.messageAdapter[ChargingStationEvents.Response] {
        case ChargingStationEvents.ChargingStationUpdated(chargingStation, ref) =>
          UpdateChargingStation(chargingStation, ref)
        case _ =>
          BadRequest()
      }

      Behaviors receiveMessage {
        case ChargingStationsUpdated(refs) =>
          refs foreach { _ ! ChargingStationEvents.AskState(csAdapter) }
          Behaviors.same
        case UpdateChargingStation(chargingStation, ref) =>
          ChargingStationProvider(chargingStations.updated(ref, chargingStation))
        case GetChargingStations(replyTo) =>
          replyTo ! chargingStations.values.toSet
          Behaviors.same
        case _ =>
          Behaviors.same
      }
    }
