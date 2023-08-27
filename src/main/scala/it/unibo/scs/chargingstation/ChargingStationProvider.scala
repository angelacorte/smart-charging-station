package it.unibo.scs.chargingstation

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors
import it.unibo.scs.chargingstation.ChargingStationActor.ChargingStationServiceKey

object ChargingStationProvider:
  private type CSModel = (ChargingStation, ChargingStationState)
  private type ChargingStationRegistry = Map[ActorRef[ChargingStationEvents.Request], CSModel]
  sealed trait Request
  case class UpdateChargingStation(chargingStation: ChargingStation, state: ChargingStationState, ref: ActorRef[ChargingStationEvents.Request]) extends Request
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
        case ChargingStationEvents.ChargingStationUpdated(chargingStation, state, ref) =>
          UpdateChargingStation(chargingStation, state, ref)
        case _ =>
          BadRequest()
      }

      Behaviors receiveMessage {
        case ChargingStationsUpdated(refs) =>
          refs foreach { _ ! ChargingStationEvents.AskState(csAdapter) }
          Behaviors.same
        case UpdateChargingStation(chargingStation, state, ref) =>
          ChargingStationProvider(chargingStations.updated(ref, (chargingStation, state)))
        case _ =>
          Behaviors.same
      }
    }
