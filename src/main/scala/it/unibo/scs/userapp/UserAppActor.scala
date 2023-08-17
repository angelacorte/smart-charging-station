package it.unibo.scs.userapp

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors

object UserAppActor:
  sealed trait Event

  val UserAppServiceKey: ServiceKey[UserAppActor.Event] = ServiceKey[UserAppActor.Event]("UserApp")

