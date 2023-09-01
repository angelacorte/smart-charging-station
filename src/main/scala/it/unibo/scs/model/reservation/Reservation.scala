package it.unibo.scs.model.reservation

import it.unibo.scs.CborSerializable

/**
 * A reservation is a request to reserve a charging station for a user.
 * @param userId The user that wants to reserve a charging station.
 * @param chargingStationId The charging station that the user wants to reserve.
 */
case class Reservation(userId: String, chargingStationId: Int) extends CborSerializable

/**
 * The result of a reservation request.
 */
sealed trait ReservationResult

/**
 * The reservation request was accepted.
 */
case class ReservationOk() extends ReservationResult with CborSerializable

/**
 * The reservation request was rejected.
 * @param reason The reason why the reservation was rejected.
 */
case class ReservationNotOk(reason: String = "") extends ReservationResult with CborSerializable