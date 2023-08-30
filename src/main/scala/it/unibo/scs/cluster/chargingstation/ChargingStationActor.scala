package it.unibo.scs.cluster.chargingstation

import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import it.unibo.scs.service.chargingstation.ChargingStationProvider.ProviderServiceKey
import it.unibo.scs.model.chargingstation.{ChargingStation, ChargingStationState, Reservation}
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

  private def free(chargingStation: ChargingStation, providers: Set[ActorRef[ChargingStationProvider.Request]]): Behavior[ChargingStationEvents.Request] =
    Behaviors receive {
      case (ctx, AskState(replyTo)) =>
        replyTo ! ChargingStationUpdated(chargingStation, ctx.self)
        free(chargingStation, providers)
      case (_, Charge(replyTo)) =>
          replyTo ! Ok()
          charging(chargingStation.copy(state = ChargingStationState.CHARGING), providers)
      case (_, Reserve(reservation, replyTo)) =>
          replyTo ! ReservationOk()
          reserved(chargingStation.copy(state = ChargingStationState.RESERVED), reservation, providers)
      case _ => free(chargingStation, providers)
    }

  private def charging(chargingStation: ChargingStation, providers: Set[ActorRef[ChargingStationProvider.Request]]): Behavior[ChargingStationEvents.Request] =
    Behaviors withTimers { timers =>
      timers.startTimerWithFixedDelay(StopCharge(), 2.seconds)
      Behaviors receive {
        case (ctx, AskState(replyTo)) =>
          replyTo ! ChargingStationUpdated(chargingStation, ctx.self)
          charging(chargingStation, providers)
        case (_, Charge(replyTo)) =>
          replyTo ! NotOk(ChargingStationState.CHARGING)
          charging(chargingStation, providers)
        case (_, Reserve(_, replyTo)) =>
          replyTo ! ReservationNotOk("This charging station is busy")
          charging(chargingStation, providers)
        case (_, StopCharge()) =>
          free(chargingStation.copy(state = ChargingStationState.FREE), providers)
        case _ => charging(chargingStation, providers)
      }
    }

  private def reserved(chargingStation: ChargingStation, reservation: Reservation, providers: Set[ActorRef[ChargingStationProvider.Request]]): Behavior[ChargingStationEvents.Request] =
    Behaviors receive {
      case (ctx, AskState(replyTo)) =>
        replyTo ! ChargingStationUpdated(chargingStation, ctx.self)
        reserved(chargingStation, reservation, providers)
      case (_, Charge(replyTo)) =>
        // TODO implement
        charging(chargingStation.copy(state = ChargingStationState.CHARGING), providers)
      case (_, Reserve(_, replyTo)) =>
        replyTo ! ReservationNotOk("This charging station is already reserved")
        reserved(chargingStation, reservation, providers)
      case _ => reserved(chargingStation, reservation, providers)
    }
