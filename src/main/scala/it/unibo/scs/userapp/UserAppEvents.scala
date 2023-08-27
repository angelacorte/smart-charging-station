package it.unibo.scs.userapp

import akka.actor.typed.ActorRef
import akka.actor.typed.delivery.internal.ProducerControllerImpl.Request
import it.unibo.scs.car.Car
import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationState}

object UserAppEvents:
  sealed trait Request

  case class Tick() extends Request

  final case class BadRequest() extends Request

  case class ChargingStationUpdated(chargingStation: ChargingStation) extends Request

  case class CarUpdated(car: Car) extends Request

  sealed trait Response
