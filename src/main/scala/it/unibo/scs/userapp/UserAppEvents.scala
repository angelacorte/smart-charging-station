package it.unibo.scs.userapp

import akka.actor.typed.ActorRef
import akka.actor.typed.delivery.internal.ProducerControllerImpl.Request
import it.unibo.scs.car.Car
import it.unibo.scs.chargingstation.ChargingStation

object UserAppEvents:
  sealed trait Request

  case class Tick() extends Request

  case class AskChargingStationState(replyTo: ActorRef[UserAppEvents.Response]) extends Request

  case class AskCarState(replyTo: ActorRef[UserAppEvents.Response]) extends Request

  final case class BadRequest() extends Request

  sealed trait Response

  case class CSUpdatedUserApp(chargingStation: ChargingStation) extends Response

  case class CarUpdatedUserApp(car: Car) extends Response
