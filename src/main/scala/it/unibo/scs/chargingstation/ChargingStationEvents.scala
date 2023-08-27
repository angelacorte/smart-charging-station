package it.unibo.scs.chargingstation

import akka.actor.typed.ActorRef
import it.unibo.scs.car.ControlUnit

object ChargingStationEvents:
  sealed trait Request
  case class AskState(replyTo: ActorRef[Response]) extends Request
  case class Charge(replyTo: ActorRef[Response]) extends Request
  case class Reserve(replyTo: ActorRef[Response]) extends Request
  case class Tick() extends Request
  case class StopCharge() extends Request
  case class ProvidersUpdated(providers: Set[ActorRef[ChargingStationProvider.Request]]) extends Request

  sealed trait Response
  case class SendChargeFromChargingStation(charge: Double, replyTo: ActorRef[ChargingStationEvents.Request]) extends Response
  case class Ok() extends Response
  case class NotOk(state: ChargingStationState) extends Response
  case class ChargingStationUpdated(chargingStation: ChargingStation, state: ChargingStationState, ref: ActorRef[ChargingStationEvents.Request]) extends Response

