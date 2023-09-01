package it.unibo.scs.model.chargerequest

import it.unibo.scs.CborSerializable

/**
 * A charge request is a request to charge a vehicle.
 * @param userId the user id
 * @param chargingStationId the charging station id
 */
case class ChargeRequest(userId: String, chargingStationId: Int) extends CborSerializable

/**
 * The result of a charge request.
 */
sealed trait ChargeRequestResult

/**
 * The request was accepted.
 */
case class ChargeRequestOk() extends ChargeRequestResult with CborSerializable

/**
 * The request was not accepted.
 * @param reason the reason why the request was not accepted
 */
case class ChargeRequestNotOk(reason: String = "") extends ChargeRequestResult with CborSerializable