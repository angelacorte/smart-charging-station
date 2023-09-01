package it.unibo.scs.cluster.chargingstation

import akka.actor.typed.ActorRef
import it.unibo.scs.CborSerializable
import it.unibo.scs.model.chargerequest.{ChargeRequest, ChargeRequestResult}
import it.unibo.scs.model.chargingstation.ChargingStation
import it.unibo.scs.model.chargingstation.ChargingStation.*
import it.unibo.scs.model.reservation.{Reservation, ReservationResult}
import it.unibo.scs.service.chargingstation.ChargingStationProvider

/**
 * This object contains the communication protocol between the [[ChargingStationActor]] and other actors.
 */
object ChargingStationEvents:
  /**
   * This trait represents the messages that can be sent to the [[ChargingStationActor]].
   */
  sealed trait Request

  /**
   * This message is sent to the [[ChargingStationActor]] to ask for the current state of the charging station.
   * @param replyTo the actor that will receive the response. In this case, the [[ChargingStationProvider]].
   */
  case class AskState(replyTo: ActorRef[ChargingStationProvider.Request]) extends Request with CborSerializable
  /**
   * This message is sent to the [[ChargingStationActor]] to start the charging process.
   * @param replyTo the actor that will receive the response. In this case, an actor ref for [[ChargeRequestResult]].
   */
  case class Charge(request: ChargeRequest, replyTo: ActorRef[ChargeRequestResult]) extends Request with CborSerializable

  /**
   * This message is sent to the [[ChargingStationActor]] to reserve the charging station.
   * @param reservation the [[Reservation]] to be made.
   * @param replyTo the actor that will receive the response. In this case, an actor ref for [[ReservationResult]].
   */
  case class Reserve(reservation: Reservation, replyTo: ActorRef[ReservationResult]) extends Request with CborSerializable
  
  /**
   * This message is sent to the [[ChargingStationActor]] to stop the charging process.
   */
  case class StopCharge() extends Request
  
  /**
   * This message is sent to the [[ChargingStationActor]] to update the [[ChargingStationProvider]] set.
   */
  case class ProvidersUpdated(providers: Set[ActorRef[ChargingStationProvider.Request]]) extends Request
  


