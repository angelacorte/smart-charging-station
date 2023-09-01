package it.unibo.scs.service

import it.unibo.scs.model.chargerequest.ChargeRequest
import it.unibo.scs.model.chargingstation.ChargingStationState
import it.unibo.scs.model.chargingstation.ChargingStationState.ChargingStationState
import it.unibo.scs.model.chargingstation.{ChargingStation, Position}
import it.unibo.scs.model.reservation.Reservation
import spray.json.DefaultJsonProtocol.*
import spray.json.enrichAny
import spray.json.{DeserializationException, JsArray, JsString, JsValue, RootJsonFormat}

/**
 * This object contains implicit marshallers and unmarshallers for requests sent to, and responses sent by the [[ChargingStationService]].
 */
object Formats:
  given stateFormatter: RootJsonFormat[ChargingStationState] with
    override def write(obj: ChargingStationState.Value): JsValue = JsString(obj.toString)

    override def read(json: JsValue): ChargingStationState = json match
      case JsString(value) => ChargingStationState.withName(value)
      case _ => throw DeserializationException("Enum string expected")

  given positionFormat: RootJsonFormat[Position] = jsonFormat2(Position.apply)

  given stationFormat: RootJsonFormat[ChargingStation] = jsonFormat6(ChargingStation.apply)

  given listFormat: RootJsonFormat[List[ChargingStation]] with
    override def write(obj: List[ChargingStation]): JsValue = JsArray(obj.map(_.toJson).toVector)

    override def read(json: JsValue): List[ChargingStation] = json match
      case JsArray(elements) => elements.map(_.convertTo[ChargingStation]).toList
      case _ => throw DeserializationException("Enum string expected")


  given chargeRequestFormat: RootJsonFormat[ChargeRequest] = jsonFormat2(ChargeRequest.apply)
  given reservationFormat: RootJsonFormat[Reservation] = jsonFormat2(Reservation.apply)