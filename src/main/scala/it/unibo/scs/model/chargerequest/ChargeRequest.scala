package it.unibo.scs.model.chargerequest

import it.unibo.scs.CborSerializable

/**
 * A charge request is a request to charge a vehicle.
 * @param userId the user id
 * @param chargingStationId the charging station id
 */
case class ChargeRequest(userId: String, chargingStationId: Int) extends CborSerializable

sealed trait ChargeRequestResult

case class ChargeRequestOk() extends ChargeRequestResult with CborSerializable

case class ChargeRequestNotOk(reason: String = "") extends ChargeRequestResult with CborSerializable