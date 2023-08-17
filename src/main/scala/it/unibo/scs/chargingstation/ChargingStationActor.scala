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
  case class AskState(csID: Int, replyTo: ActorRef[Any]) extends Event //Any temporary, should be as above
//  case class AskState(csID: Int, replyTo: ActorRef[UserAppActor]) extends Event
  private case class Tick() extends Event
  private case class UserAppUpdated(newSet: Set[ActorRef[UserAppActor.Event]]) extends Event
  private case class CarUpdated(newSet: Set[ActorRef[CarActor.Event]]) extends Event


  val ChargingStationServiceKey: ServiceKey[ChargingStationActor.Event] = ServiceKey[ChargingStationActor.Event]("ChargingStation")

  def apply(chargingStation: ChargingStation): Behavior[ChargingStationActor.Event] =
    Behaviors withTimers { timers =>
      Behaviors setup { ctx =>
        val subscriptionAdapter = ctx.messageAdapter(Receptionist.Listing) {
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
      case AskState(chargingStationID, replyTo) => ???
//        if chargingStationID == chargingStation.id then
//          replyTo ! ???
    }

