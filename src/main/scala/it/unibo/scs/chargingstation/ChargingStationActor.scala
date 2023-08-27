package it.unibo.scs.chargingstation

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import it.unibo.scs.car.{Car, ControlUnit}
import it.unibo.scs.car.ControlUnit.{CarUpdated, SendCharge}
import it.unibo.scs.chargingstation.ChargingStationProvider
import it.unibo.scs.chargingstation.ChargingStationProvider.ProviderServiceKey
import it.unibo.scs.userapp.UserAppActor

import javax.swing.event.DocumentEvent.EventType
import concurrent.duration.DurationInt

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
        replyTo ! ChargingStationUpdated(chargingStation, ChargingStationState.FREE, ctx.self)
        Behaviors.same
      case (_, Charge(replyTo)) =>
          replyTo ! Ok()
          charging(chargingStation, replyTo, providers)
      case (_, Reserve(replyTo)) =>
          replyTo ! Ok()
          reserved(chargingStation, replyTo, providers)
      case _ => Behaviors.same
    }

  private def charging(chargingStation: ChargingStation, replyTo: ActorRef[Response], providers: Set[ActorRef[ChargingStationProvider.Request]]): Behavior[ChargingStationEvents.Request] =
    Behaviors withTimers { timers =>
      timers.startTimerWithFixedDelay(Tick(), 2.seconds)
      Behaviors receive {
        case (ctx, AskState(replyTo)) =>
          replyTo ! ChargingStationUpdated(chargingStation, ChargingStationState.CHARGING, ctx.self)
          charging(chargingStation, replyTo, providers)
        case (_, Charge(replyTo)) =>
          replyTo ! NotOk(ChargingStationState.CHARGING)
          charging(chargingStation, replyTo, providers)
        case (_, Reserve(replyTo)) =>
          replyTo ! NotOk(ChargingStationState.CHARGING)
          charging(chargingStation, replyTo, providers)
        case (_, StopCharge()) =>
          free(chargingStation, providers)
        case (ctx, Tick()) =>
          replyTo ! SendChargeFromChargingStation(5.0, ctx.self)
          charging(chargingStation, replyTo, providers)
        case _ => charging(chargingStation, replyTo, providers)
      }
    }

  private def reserved(chargingStation: ChargingStation, replyTo: ActorRef[Response], providers: Set[ActorRef[ChargingStationProvider.Request]]): Behavior[ChargingStationEvents.Request] =
    Behaviors receive {
      case (ctx, AskState(replyTo)) =>
        replyTo ! ChargingStationUpdated(chargingStation, ChargingStationState.RESERVED, ctx.self)
        reserved(chargingStation, replyTo, providers)
      case (_, Charge(replyTo)) =>
        /**
         * Contattare un db o altro per capire chi ha prenotato la colonnina
         * la risposta va di conseguenza
         */
        replyTo ! NotOk(ChargingStationState.RESERVED)
        charging(chargingStation, replyTo, providers)
      case _ => reserved(chargingStation, replyTo, providers)
    }
