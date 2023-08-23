package it.unibo.scs.car

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import it.unibo.scs.car.BatteryStatus.{On, Off}

import concurrent.duration.DurationInt

object BatteryActor:
  sealed trait Command
  case class TurnOn() extends Command
  case class TurnOff() extends Command
  private case class Charge(amount: Double) extends Command
  private case class Discharge() extends Command
  final case class DebugBattery(replyTo: ActorRef[BatteryUpdated]) extends Command
  final case class BatteryUpdated(battery: Battery, status: BatteryStatus)
  

  def apply(battery: Battery, cu: ActorRef[ControlUnit.Request]): Behavior[BatteryActor.Command] =
    off(battery, cu)

  private def on(battery: Battery, cu: ActorRef[ControlUnit.Request]): Behavior[BatteryActor.Command] =
    Behaviors withTimers { timers =>
      timers.startTimerWithFixedDelay(Discharge(), 1.seconds)

      Behaviors receiveMessage {
        case Discharge() =>
          println(s"BatteryActor: Discharge, current charge: ${battery.currentCharge}")
          cu ! ControlUnit.BatteryUpdated(battery.discharge, On)
          on(battery.discharge, cu)
        case TurnOff() =>
          println("BatteryActor: TurnOff")
          off(battery, cu)
        case DebugBattery(replyTo) =>
          replyTo ! BatteryUpdated(battery, On)
          on(battery, cu)
        case _ =>
          on(battery, cu)
      }
    }

  private def off(battery: Battery, cu: ActorRef[ControlUnit.Request]): Behavior[BatteryActor.Command] =
    Behaviors receiveMessage {
      case TurnOn() =>
        println("BatteryActor: TurnOn")
        on(battery, cu)
      case Charge(amount) =>
        println(s"BatteryActor: Charge, current charge: ${battery.currentCharge}")
        cu ! ControlUnit.BatteryUpdated(battery.charge(amount), Off)
        off(battery.charge(amount), cu)
      case DebugBattery(replyTo) =>
        replyTo ! BatteryUpdated(battery, Off)
        off(battery, cu)
      case _ =>
        off(battery, cu)
    }
