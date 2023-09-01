package it.unibo.scs.model.reservation

import it.unibo.scs.CborSerializable

case class Reservation(userId: String, chargingStationId: Int) extends CborSerializable