package it.unibo.scs.chargingstation

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import it.unibo.scs.car.CarActor
import it.unibo.scs.userapp.UserAppActor

import javax.swing.event.DocumentEvent.EventType
import concurrent.duration.DurationInt

object ChargingStationActor:
  sealed trait Event
  case class UpdateChargingStation(chargingStation: ChargingStation)
  case class SendState(csID: Int, replyTo: ActorRef[UpdateChargingStation]) extends Event //Any temporary, should be as above
  case class ChangeState(csID: Int, newState: ChargingStationState, replyTo: ActorRef[UpdateChargingStation]) extends Event
  private case class Tick() extends Event
  private case class UserAppUpdated(newSet: Set[ActorRef[UserAppActor.Event]]) extends Event
  private case class CarUpdated(newSet: Set[ActorRef[CarActor.Event]]) extends Event


  val ChargingStationServiceKey: ServiceKey[ChargingStationActor.Event] = ServiceKey[ChargingStationActor.Event]("ChargingStation")

  def apply(chargingStation: ChargingStation): Behavior[ChargingStationActor.Event] =
    Behaviors withTimers { timers =>
      Behaviors setup { ctx =>
        val subscriptionAdapter = ctx.messageAdapter[Receptionist.Listing] {
          case UserAppActor.UserAppServiceKey.Listing(newSet) => UserAppUpdated(newSet)
          case CarActor.CarServiceKey.Listing(newSet) => CarUpdated(newSet)
        }

        ctx.system.receptionist ! Receptionist.Subscribe(CarActor.CarServiceKey, subscriptionAdapter)
        ctx.system.receptionist ! Receptionist.Subscribe(UserAppActor.UserAppServiceKey, subscriptionAdapter)
        ctx.system.receptionist ! Receptionist.Register(ChargingStationServiceKey, ctx.self)

        timers.startTimerWithFixedDelay(Tick(), Tick(), 10.seconds)
        running(ctx, Set.empty, Set.empty, chargingStation)
      }
    }

  private def running(ctx: ActorContext[Event],
                      cars: Set[ActorRef[CarActor.Event]],
                      userApp: Set[ActorRef[UserAppActor.Event]],
                      chargingStation: ChargingStation ) : Behavior[ChargingStationActor.Event] =
    Behaviors receiveMessage {
      case SendState(chargingStationID, replyTo) =>
        if chargingStationID == chargingStation.id then
          replyTo ! UpdateChargingStation(chargingStation)
        Behaviors.same
      case ChangeState(chargingStationID, newState, replyTo) =>
        if chargingStationID == chargingStation.id then
          val newCS = ChargingStation(id = chargingStation.id, state = newState, location = chargingStation.location)
          replyTo ! UpdateChargingStation(newCS)
          newState match
            case ChargingStationState.FREE => running(ctx, cars, userApp, newCS) //should not happen
            case ChargingStationState.CHARGING => charging(newCS)
            case ChargingStationState.RESERVED => reserved(newCS)
            case ChargingStationState.UNAVAILABLE => unavailable(newCS)
        else
          Behaviors.same
      case _ => Behaviors.same
    }

  private def charging(chargingStation: ChargingStation): Behavior[ChargingStationActor.Event] =
    Behaviors receiveMessage {

      case _ => Behaviors.same
    }

  private def reserved(chargingStation: ChargingStation): Behavior[ChargingStationActor.Event] =
    Behaviors receiveMessage {
      case _ => Behaviors.same
    }

  private def unavailable(chargingStation: ChargingStation): Behavior[ChargingStationActor.Event] =
    Behaviors receiveMessage {
      case _ => Behaviors.same
    }

