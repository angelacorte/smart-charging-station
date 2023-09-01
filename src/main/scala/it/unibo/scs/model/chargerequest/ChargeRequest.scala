package it.unibo.scs.model.chargerequest

import it.unibo.scs.CborSerializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

/**
 * A charge request is a request to charge a vehicle.
 * @param userId the user id
 * @param chargingStationId the charging station id
 */
case class ChargeRequest(userId: String, chargingStationId: Int) extends CborSerializable