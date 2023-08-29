package it.unibo.scs.cluster.chargingstation

import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import it.unibo.scs.service.chargingstation.ChargingStationProvider.ProviderServiceKey
import it.unibo.scs.model.chargingstation.{ChargingStation, ChargingStationState}
import it.unibo.scs.service.chargingstation.ChargingStationProvider

import scala.concurrent.duration.DurationInt

object ChargingStationActor:
  import ChargingStationEvents.*

  val ChargingStationServiceKey: ServiceKey[ChargingStationEvents.Request] = ServiceKey[ChargingStationEvents.Request]("ChargingStation")

  def apply(chargingStation: ChargingStation, providers: Set[ActorRef[ChargingStationProvider.Request]] = Set.empty): Behavior[ChargingStationEvents.Request] =
    Behaviors.setup { ctx =>
      val subscriptionAdapter = ctx.messageAdapter[Receptionist.Listing] {
        case ChargingStationProvider.ProviderServiceKey.Listing(providers) =>
          ProvidersUpdated(providers)
      }
      ctx.system.receptionist ! Receptionist.Subscribe(ProviderServiceKey, subscriptionAdapter)
      ctx.system.receptionist ! Receptionist.Register(ChargingStationServiceKey, ctx.self)
      free(chargingStation, providers)
    }

  private def free(chargingStation: ChargingStation, providers: Set[ActorRef[ChargingStationProvider.Request]]) : Behavior[ChargingStationEvents.Request] =
    Behaviors receive {
      case (ctx, AskState(replyTo)) =>
        replyTo ! ChargingStationUpdated(chargingStation, ctx.self)
        free(chargingStation, providers)
      case (_, Charge(replyTo)) =>
          replyTo ! Ok()
          charging(chargingStation.copy(state = ChargingStationState.CHARGING), replyTo, providers)
      case (_, Reserve(replyTo)) =>
          replyTo ! Ok()
          reserved(chargingStation.copy(state = ChargingStationState.RESERVED), replyTo, providers)
      case _ => free(chargingStation, providers)
    }

  private def charging(chargingStation: ChargingStation, replyTo: ActorRef[Response], providers: Set[ActorRef[ChargingStationProvider.Request]]): Behavior[ChargingStationEvents.Request] =
    Behaviors withTimers { timers =>
      timers.startTimerWithFixedDelay(Tick(), 2.seconds)
      Behaviors receive {
        case (ctx, AskState(replyTo)) =>
          replyTo ! ChargingStationUpdated(chargingStation, ctx.self)
          charging(chargingStation, replyTo, providers)
        case (_, Charge(replyTo)) =>
          replyTo ! NotOk(ChargingStationState.CHARGING)
          charging(chargingStation, replyTo, providers)
        case (_, Reserve(replyTo)) =>
          replyTo ! NotOk(ChargingStationState.CHARGING)
          charging(chargingStation, replyTo, providers)
        case (_, StopCharge()) =>
          free(chargingStation.copy(state = ChargingStationState.FREE), providers)
        case (ctx, Tick()) =>
          replyTo ! SendChargeFromChargingStation(5.0, ctx.self)
          charging(chargingStation, replyTo, providers)
        case _ => charging(chargingStation, replyTo, providers)
      }
    }

  private def reserved(chargingStation: ChargingStation, replyTo: ActorRef[Response], providers: Set[ActorRef[ChargingStationProvider.Request]]): Behavior[ChargingStationEvents.Request] =
    Behaviors receive {
      case (ctx, AskState(replyTo)) =>
        replyTo ! ChargingStationUpdated(chargingStation, ctx.self)
        reserved(chargingStation, replyTo, providers)
      case (_, Charge(replyTo)) =>
        /**
         * Contattare un db o altro per capire chi ha prenotato la colonnina
         * la risposta va di conseguenza
         */
        replyTo ! NotOk(ChargingStationState.RESERVED)
        charging(chargingStation.copy(state = ChargingStationState.CHARGING), replyTo, providers)
      case _ => reserved(chargingStation, replyTo, providers)
    }
