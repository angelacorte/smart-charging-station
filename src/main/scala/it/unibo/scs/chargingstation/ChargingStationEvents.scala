package it.unibo.scs.chargingstation

import akka.actor.typed.ActorRef
import it.unibo.scs.car.CarActor

object ChargingStationEvents:
  sealed trait Request

  case class AskState(replyTo: ActorRef[Response]) extends Request

  case class Charge(replyTo: ActorRef[Response]) extends Request

  case class Reserve(replyTo: ActorRef[Response]) extends Request

  case class Tick() extends Request

  case class SendChargeFromChargingStation(charge: Double, replyTo: ActorRef[ChargingStationEvents.Request]) extends Response

  case class Ok() extends Response

  case class NotOk(state: ChargingStationState) extends Response

  sealed trait Response

  case class ChargingStationUpdated(chargingStation: ChargingStation) extends Response

  case class StopCharge() extends Request
