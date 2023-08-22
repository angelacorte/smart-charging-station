package it.unibo.scs.userapp

import akka.actor.typed.{ActorRef, Behavior, BehaviorSignalInterceptor}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.util.Timeout
import it.unibo.scs.car.{Battery, Car, ControlUnit}
import it.unibo.scs.chargingstation.ChargingStationEvents.*
import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor, ChargingStationEvents}
import it.unibo.scs.userapp.{UserApp, UserAppEvents}

import concurrent.duration.DurationInt

object UserAppActor:
  import UserAppEvents.*

  def apply(controlUnits: Set[ActorRef[ControlUnit.Request]], chargingStations: Set[ActorRef[ChargingStationEvents.Request]]): Behavior[UserAppEvents.Request] =
    Behaviors setup { context =>

      Behaviors withTimers { timers =>
        timers.startTimerWithFixedDelay(Tick(), 2.seconds)

        val controlUnitAdapter = context messageAdapter {
          case ControlUnit.CarUpdated(car) => UserAppEvents.CarUpdated(car)
          case _ => BadRequest()
        }

        val chargingStationAdapter = context messageAdapter {
          case ChargingStationEvents.ChargingStationUpdated(chargingStation) =>
            UserAppEvents.ChargingStationUpdated(chargingStation)
          case _ => BadRequest()
        }

        Behaviors receiveMessage {
          case UserAppEvents.ChargingStationUpdated(chargingStation) =>
            context.log.info(s"Charging station status $chargingStation")
            Behaviors.same
          case UserAppEvents.CarUpdated(car) =>
            context.log.info(s"Car status $car")
            Behaviors.same
          case Tick() =>
            controlUnits foreach { c =>
              c ! ControlUnit.AskState(controlUnitAdapter)
            }
            chargingStations foreach { cs =>
              cs ! ChargingStationEvents.AskState(chargingStationAdapter)
            }
            Behaviors.same
          case _ => Behaviors.same
        }
      }
    }
