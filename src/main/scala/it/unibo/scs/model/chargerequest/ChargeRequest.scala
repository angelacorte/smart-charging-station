package it.unibo.scs.model.chargerequest

import it.unibo.scs.CborSerializable
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat


case class ChargeRequest(userId: String, chargingStationId: Int) extends CborSerializable

object ChargeRequest:
  object Formats:
    given chargeRequestFormat: RootJsonFormat[ChargeRequest] = jsonFormat2(ChargeRequest.apply)