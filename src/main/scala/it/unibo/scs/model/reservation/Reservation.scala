package it.unibo.scs.model.reservation

import it.unibo.scs.CborSerializable

/**
 * A reservation is a request to reserve a charging station for a user.
 * @param userId The user that wants to reserve a charging station.
 * @param chargingStationId The charging station that the user wants to reserve.
 */
case class Reservation(userId: String, chargingStationId: Int) extends CborSerializable

sealed trait ReservationResult

case class ReservationOk() extends ReservationResult with CborSerializable

case class ReservationNotOk(reason: String = "") extends ReservationResult with CborSerializable