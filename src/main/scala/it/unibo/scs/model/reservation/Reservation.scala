package it.unibo.scs.model.reservation

import it.unibo.scs.CborSerializable
import spray.json.DefaultJsonProtocol.*
import spray.json.RootJsonFormat

case class Reservation(userId: Int, chargingStationId: Int) extends CborSerializable

object Reservation:
  object Formats:
    given reservationFormat: RootJsonFormat[Reservation] = jsonFormat2(Reservation.apply)
