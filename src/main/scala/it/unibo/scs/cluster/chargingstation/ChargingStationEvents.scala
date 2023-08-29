package it.unibo.scs.cluster.chargingstation

import akka.actor.typed.ActorRef
import it.unibo.scs.CborSerializable
import it.unibo.scs.model.chargingstation.ChargingStation
import it.unibo.scs.model.chargingstation.ChargingStation.*
import it.unibo.scs.model.chargingstation.ChargingStationState.ChargingStationState
import it.unibo.scs.service.chargingstation.ChargingStationProvider

object ChargingStationEvents:
  sealed trait Request
  case class AskState(replyTo: ActorRef[Response]) extends Request with CborSerializable
  case class Charge(replyTo: ActorRef[Response]) extends Request
  case class Reserve(replyTo: ActorRef[ReservationResult]) extends Request with CborSerializable
  case class Tick() extends Request
  case class StopCharge() extends Request
  case class ProvidersUpdated(providers: Set[ActorRef[ChargingStationProvider.Request]]) extends Request

  sealed trait Response
  case class Ok() extends Response
  case class NotOk(state: ChargingStationState) extends Response
  case class ChargingStationUpdated(chargingStation: ChargingStation, ref: ActorRef[ChargingStationEvents.Request]) extends Response with CborSerializable
  
  sealed trait ReservationResult
  case class ReservationOk() extends ReservationResult with CborSerializable
  case class ReservationNotOk(reason: String = "") extends ReservationResult with CborSerializable

