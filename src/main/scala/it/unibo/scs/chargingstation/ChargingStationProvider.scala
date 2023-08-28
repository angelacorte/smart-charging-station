package it.unibo.scs.chargingstation

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors
import it.unibo.scs.CborSerializable
import it.unibo.scs.chargingstation.ChargingStationActor.ChargingStationServiceKey

object ChargingStationProvider:
  private type ChargingStationRegistry = Map[ActorRef[ChargingStationEvents.Request], ChargingStation]
  sealed trait Request
  case class GetChargingStations(replyTo: ActorRef[Set[ChargingStation]]) extends Request with CborSerializable
  case class UpdateChargingStation(chargingStation: ChargingStation, ref: ActorRef[ChargingStationEvents.Request]) extends Request with CborSerializable
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
      ctx.system.receptionist ! Receptionist.Register(ProviderServiceKey, ctx.self)



      /** SPAWN CS SERVICE */
      ctx.spawn(ChargingStationService(ctx.self), "ChargingStationService")

      running(chargingStations)
    }

  private def running(chargingStations: ChargingStationRegistry = Map.empty): Behavior[Request] =
    Behaviors receive {
      case (ctx, ChargingStationsUpdated(refs)) =>
        ctx.log.info("ChargingStationsUpdated: {}", refs)
        val csAdapter = ctx.messageAdapter[ChargingStationEvents.Response] {
          case ChargingStationEvents.ChargingStationUpdated(chargingStation, ref) =>
            UpdateChargingStation(chargingStation, ref)
          case _ =>
            BadRequest()
        }
        refs foreach {
          _ ! ChargingStationEvents.AskState(csAdapter)
        }
        running(chargingStations)
      case (ctx, UpdateChargingStation(chargingStation, ref)) =>
        ctx.log.info("UpdateChargingStation: {}", chargingStation)
        running(chargingStations.updated(ref, chargingStation))
      case (ctx, GetChargingStations(replyTo)) =>
        ctx.log.info("GetChargingStations: {}", chargingStations.values.toSet)
        replyTo ! chargingStations.values.toSet
        running(chargingStations)
      case _ =>
        running(chargingStations)
    }