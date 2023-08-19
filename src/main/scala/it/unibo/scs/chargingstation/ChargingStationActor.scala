package it.unibo.scs.chargingstation

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import it.unibo.scs.car.{Car, CarActor}
import it.unibo.scs.car.CarActor.{CarUpdated, SendCharge}
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
        replyTo ! ChargingStationUpdated(chargingStation)
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
      Behaviors receiveMessage {
        case Charge(replyTo) =>
          replyTo ! NotOk(ChargingStationState.CHARGING)
          charging(chargingStation, replyTo)
        case Reserve(replyTo) =>
          replyTo ! NotOk(ChargingStationState.CHARGING)
          charging(chargingStation, replyTo)
        case StopCharge() =>
          free(chargingStation)
        case Tick() =>
          replyTo ! SendChargeFromChargingStation(5.0)
          charging(chargingStation, replyTo)
        case _ => charging(chargingStation, replyTo)
      }
    }

  private def reserved(chargingStation: ChargingStation, replyTo: ActorRef[Response]): Behavior[ChargingStationEvents.Request] =
    Behaviors receiveMessage {
      case Charge(replyTo) =>
        /**
         * Contattare un db o altro per capire chi ha prenotato la colonnina
         * la risposta va di conseguenza
         */
        replyTo ! NotOk(ChargingStationState.RESERVED)
        charging(chargingStation, replyTo)
      case _ => reserved(chargingStation, replyTo)
    }
