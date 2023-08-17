package it.unibo.scs.car

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import it.unibo.scs.car.Car

import concurrent.duration.DurationInt

object CarActor:
  sealed trait Event
  case class SendCharge(amount: Double, replyTo: ActorRef[CarUpdated]) extends Event
  case class StartCharge() extends Event
  case class StopCharge() extends Event
  case class CarUpdated(car: Car)

  def apply(car: Car): Behavior[CarActor.Event] =
    running(car)

  private def running(car: Car): Behavior[CarActor.Event] =
    Behaviors receiveMessage {
      case StartCharge() =>
        charging(car)
      case _ => Behaviors.same
    }

  private def charging(car: Car): Behavior[CarActor.Event] =
    Behaviors receiveMessage {
      case SendCharge(amount, replyTo) =>
        val total = car.charge + amount
        replyTo ! CarUpdated(Car(total))
        if total < 1 then
          charging(Car(total))
        else
          running(Car(total))
      case StopCharge() =>
        CarActor(car)
      case _ => Behaviors.same
    }