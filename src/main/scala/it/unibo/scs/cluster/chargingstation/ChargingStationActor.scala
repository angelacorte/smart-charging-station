package it.unibo.scs.cluster.chargingstation

import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import it.unibo.scs.service.chargingstation.ChargingStationProvider.ProviderServiceKey
import it.unibo.scs.model.chargingstation.{ChargingStation, ChargingStationState}
import it.unibo.scs.model.reservation.Reservation
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
      case (_, ProvidersUpdated(providers)) =>
        free(chargingStation, providers)
      case (ctx, AskState(replyTo)) =>
        replyTo ! ChargingStationUpdated(chargingStation, ctx.self)
        free(chargingStation, providers)
      case (ctx, Charge(_, replyTo)) =>
        val newStation = chargingStation.toCharging
        providers.foreach(_ ! UpdateChargingStation(newStation, ctx.self))
        replyTo ! ChargeRequestOk()
        charging(newStation, providers)
      case (ctx, Reserve(reservation, replyTo)) =>
        val newStation = chargingStation.toReserved
        providers.foreach(_ ! UpdateChargingStation(newStation, ctx.self))
        replyTo ! ReservationOk()
        reserved(newStation, reservation, providers)
      case _ => free(chargingStation, providers)
    }

  private def charging(chargingStation: ChargingStation, providers: Set[ActorRef[ChargingStationProvider.Request]]): Behavior[ChargingStationEvents.Request] =
    Behaviors withTimers { timers =>
      timers.startTimerWithFixedDelay(StopCharge(), 10.seconds)
      Behaviors receive {
        case (_, ProvidersUpdated(providers)) =>
          charging(chargingStation, providers)
        case (ctx, AskState(replyTo)) =>
          replyTo ! ChargingStationUpdated(chargingStation, ctx.self)
          charging(chargingStation, providers)
        case (_, Charge(_, replyTo)) =>
          replyTo ! ChargeRequestNotOk("This charging station is busy")
          charging(chargingStation, providers)
        case (_, Reserve(_, replyTo)) =>
          replyTo ! ReservationNotOk("This charging station is busy")
          charging(chargingStation, providers)
        case (ctx, StopCharge()) =>
          val newStation = chargingStation.toFree
          providers.foreach(_ ! UpdateChargingStation(newStation, ctx.self))
          free(newStation, providers)
        case _ => charging(chargingStation, providers)
      }
    }

  private def reserved(chargingStation: ChargingStation, reservation: Reservation, providers: Set[ActorRef[ChargingStationProvider.Request]]): Behavior[ChargingStationEvents.Request] =
    Behaviors receive {
      case (_, ProvidersUpdated(providers)) =>
        reserved(chargingStation, reservation, providers)
      case (ctx, AskState(replyTo)) =>
        replyTo ! ChargingStationUpdated(chargingStation, ctx.self)
        reserved(chargingStation, reservation, providers)
      case (ctx, Charge(request, replyTo)) =>
        if request.userId equals reservation.userId then
          val newStation = chargingStation.toCharging
          providers.foreach(_ ! UpdateChargingStation(newStation, ctx.self))
          replyTo ! ChargeRequestOk()
          charging(newStation, providers)
        else
          replyTo ! ChargeRequestNotOk("This charging station is reserved")
          reserved(chargingStation, reservation, providers)
      case (_, Reserve(_, replyTo)) =>
        replyTo ! ReservationNotOk("This charging station is already reserved")
        reserved(chargingStation, reservation, providers)
      case _ => reserved(chargingStation, reservation, providers)
    }
