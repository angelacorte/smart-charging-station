package it.unibo.scs.userapp

import akka.actor.typed.ActorRef
import akka.actor.typed.delivery.internal.ProducerControllerImpl.Request
import it.unibo.scs.car.Car
import it.unibo.scs.chargingstation.ChargingStation

object UserAppEvents:
  sealed trait Request

  case class Tick() extends Request

  case class CSUpdatedUserApp(chargingStation: ChargingStation) extends Request

  case class CarUpdatedUserApp(car: Car) extends Request

  final case class BadRequest() extends Request

  sealed trait Response

  case class AskChargingStationState(replyTo: ActorRef[UserAppEvents.Response]) extends Response

  case class AskCarState(replyTo: ActorRef[UserAppEvents.Response]) extends Response
