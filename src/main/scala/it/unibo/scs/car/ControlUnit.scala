package it.unibo.scs.car

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import it.unibo.scs.car.Car
import it.unibo.scs.chargingstation.ChargingStationEvents
import it.unibo.scs.chargingstation.ChargingStationEvents.{Response, SendChargeFromChargingStation}

import concurrent.duration.DurationInt

object ControlUnit:
  sealed trait Request
  private final case class SendCharge(amount: Double, replyTo: ActorRef[ChargingStationEvents.Request]) extends Request
  case class AskState(replyTo: ActorRef[ControlUnit.Response]) extends Request
  case class StartCar() extends Request
  case class ChargeEnded() extends Request
  case class BatteryUpdated(battery: Battery) extends Request
  private final case class BadRequest() extends Request

  sealed trait Response
  case class CarUpdated(car: Car) extends Response
  

  def apply(car: Car): Behavior[ControlUnit.Request] =
    Behaviors.setup { context =>
      context.messageAdapter {
        case SendChargeFromChargingStation(amount, replyTo) =>
          SendCharge(amount, replyTo)
        case _ =>
          BadRequest()
      }
      
      val batteryActor = context.spawn(BatteryActor(car.battery, context.self), "batteryActor")
      
      Behaviors.receiveMessage {
        case AskState(replyTo) =>
          replyTo ! CarUpdated(car)
          Behaviors.same
        case StartCar() =>
          batteryActor ! BatteryActor.TurnOn()
          running(car)
        case _ =>
          Behaviors.same
      }
    }

  private def running(car: Car): Behavior[ControlUnit.Request] =
    Behaviors receiveMessage {
      case AskState(replyTo) =>
        replyTo ! CarUpdated(car)
        running(car)
      case BatteryUpdated(battery) =>
        println(s"Battery updated: $battery")
        running(car.copy(battery = battery))
      case _ => running(car)
    }