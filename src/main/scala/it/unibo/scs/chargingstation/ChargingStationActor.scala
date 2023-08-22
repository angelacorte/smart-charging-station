package it.unibo.scs.chargingstation

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import it.unibo.scs.car.{Car, ControlUnit}
import it.unibo.scs.car.ControlUnit.{CarUpdated, SendCharge}
import it.unibo.scs.userapp.UserAppActor

import javax.swing.event.DocumentEvent.EventType
import concurrent.duration.DurationInt

object ChargingStationActor:
  import ChargingStationEvents.*


  def apply(chargingStation: ChargingStation): Behavior[ChargingStationEvents.Request] =
    free(chargingStation)

  private def free(chargingStation: ChargingStation) : Behavior[ChargingStationEvents.Request] =
    Behaviors receiveMessage {
      case AskState(replyTo) =>
        replyTo ! ChargingStationUpdated(chargingStation, ChargingStationState.FREE)
        Behaviors.same
      case Charge(replyTo) =>
          replyTo ! Ok()
          charging(chargingStation, replyTo)
      case Reserve(replyTo) =>
          replyTo ! Ok()
          reserved(chargingStation, replyTo)
      case _ => Behaviors.same
    }

  private def charging(chargingStation: ChargingStation, replyTo: ActorRef[Response]): Behavior[ChargingStationEvents.Request] =
    Behaviors withTimers { timers =>
      timers.startTimerWithFixedDelay(Tick(), 2.seconds)
      Behaviors receive {
        case (_, AskState(replyTo)) =>
          replyTo ! ChargingStationUpdated(chargingStation, ChargingStationState.CHARGING)
          charging(chargingStation, replyTo)
        case (_, Charge(replyTo)) =>
          replyTo ! NotOk(ChargingStationState.CHARGING)
          charging(chargingStation, replyTo)
        case (_, Reserve(replyTo)) =>
          replyTo ! NotOk(ChargingStationState.CHARGING)
          charging(chargingStation, replyTo)
        case (_, StopCharge()) =>
          free(chargingStation)
        case (ctx, Tick()) =>
          replyTo ! SendChargeFromChargingStation(5.0, ctx.self)
          charging(chargingStation, replyTo)
        case _ => charging(chargingStation, replyTo)
      }
    }

  private def reserved(chargingStation: ChargingStation, replyTo: ActorRef[Response]): Behavior[ChargingStationEvents.Request] =
    Behaviors receiveMessage {
      case AskState(replyTo) =>
        replyTo ! ChargingStationUpdated(chargingStation, ChargingStationState.RESERVED)
        reserved(chargingStation, replyTo)
      case Charge(replyTo) =>
        /**
         * Contattare un db o altro per capire chi ha prenotato la colonnina
         * la risposta va di conseguenza
         */
        replyTo ! NotOk(ChargingStationState.RESERVED)
        charging(chargingStation, replyTo)
      case _ => reserved(chargingStation, replyTo)
    }
