package it.unibo.scs.car

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import it.unibo.scs.car.Car
import it.unibo.scs.chargingstation.ChargingStationEvents
import it.unibo.scs.chargingstation.ChargingStationEvents.{Response, SendChargeFromChargingStation}

import concurrent.duration.DurationInt

object CarActor:
  sealed trait Request
  private final case class SendCharge(amount: Double, replyTo: ActorRef[ChargingStationEvents.Request]) extends Request
  private final case class Discharge(amount: Double) extends Request
  case class AskState(replyTo: ActorRef[CarActor.Response]) extends Request
  case class StartCar() extends Request
  case class ChargeEnded() extends Request
  private final case class BadRequest() extends Request
  
  sealed trait Response
  case class CarUpdated(car: Car) extends Response
  

  def apply(car: Car): Behavior[CarActor.Request] =
    Behaviors.setup { context =>
      context.messageAdapter {
        case SendChargeFromChargingStation(amount, replyTo) =>
          SendCharge(amount, replyTo)
        case _ =>
          BadRequest()
      }
      Behaviors.receiveMessage {
        case AskState(replyTo) =>
          replyTo ! CarUpdated(car)
          Behaviors.same
        case StartCar() =>
          running(car)
        case _ =>
          Behaviors.same
      }
    }
  
  private def running(car: Car): Behavior[CarActor.Request] =
    Behaviors withTimers { timers =>
      timers.startTimerWithFixedDelay("discharge", Discharge(0.1), 1.seconds)

      Behaviors receiveMessage {
        case AskState(replyTo) =>
          replyTo ! CarUpdated(car)
          running(car)
        case Discharge(amount) =>
          val total = car.charge - amount
          if total <= 0.5 then
            charging(Car(total))
          else
            running(Car(total))
        case _ => running(car)
      }
    }


  private def charging(car: Car): Behavior[CarActor.Request] =
    Behaviors receiveMessage {
      case SendCharge(amount, replyTo) =>
        val total = car.charge + amount
        if total < 1 then
          charging(Car(total))
        else
          replyTo ! ChargingStationEvents.StopCharge()
          running(Car(total))
      case ChargeEnded() =>
        running(car)
      case _ => charging(car)
    }