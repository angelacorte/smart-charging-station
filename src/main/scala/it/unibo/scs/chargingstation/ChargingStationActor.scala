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
        if chargingStation.state ==  ChargingStationState.FREE then
          replyTo ! Ok()
          charging(chargingStation, replyTo)
        else
          replyTo ! NotOk(chargingStation.state)
          free(chargingStation)
      /*case Reserve(replyTo) =>
        if chargingStation.state ==  ChargingStationState.FREE then
          replyTo ! Response.Ok
          reserved(chargingStation)
        else
          replyTo ! Response.NotOk(chargingStation.state)
          free(chargingStation)*/
      case _ => Behaviors.same
    }

  private def charging(chargingStation: ChargingStation, replyTo: ActorRef[Response]): Behavior[ChargingStationEvents.Request] =
    Behaviors withTimers { timers =>
      timers.startTimerWithFixedDelay(Tick(), 2.seconds)
      Behaviors receiveMessage {
        case StopCharge() => free(chargingStation)
        case Tick() =>
          replyTo ! SendChargeFromChargingStation(5.0)
          charging(chargingStation, replyTo)
        case _ => Behaviors.same
      }
    }

  private def reserved(chargingStation: ChargingStation): Behavior[ChargingStationEvents.Response] =
    Behaviors receiveMessage {
      case _ => Behaviors.same
    }

  private def unavailable(chargingStation: ChargingStation): Behavior[ChargingStationEvents.Response] =
    Behaviors receiveMessage {
      case _ => Behaviors.same
    }
