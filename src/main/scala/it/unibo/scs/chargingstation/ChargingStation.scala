package it.unibo.scs.chargingstation

import it.unibo.scs.CborSerializable
import spray.json.DefaultJsonProtocol.*
import spray.json.{DeserializationException, JsArray, JsString, JsValue, RootJsonFormat}
import spray.json.enrichAny

object ChargingStation:
  object ChargingStationState extends Enumeration with Serializable:
    type ChargingStationState = Value
    val FREE, CHARGING, RESERVED, UNAVAILABLE = Value

  import ChargingStationState._
  
  case class ChargingStation(id: Int, state: ChargingStationState = ChargingStationState.FREE) extends CborSerializable

  object Formats:
    given stateFormatter: RootJsonFormat[ChargingStationState] with
      override def write(obj: ChargingStationState.Value): JsValue = JsString(obj.toString)
      override def read(json: JsValue): ChargingStationState = json match
        case JsString(value) => ChargingStationState.withName(value)
        case _ => throw DeserializationException("Enum string expected")
        
    given stationFormat: RootJsonFormat[ChargingStation] = jsonFormat2(ChargingStation.apply)

    given listFormat: RootJsonFormat[List[ChargingStation]] with
      override def write(obj: List[ChargingStation]): JsValue = JsArray(obj.map(_.toJson).toVector)
      override def read(json: JsValue): List[ChargingStation] = json match
        case JsArray(elements) => elements.map(_.convertTo[ChargingStation]).toList
        case _ => throw DeserializationException("Enum string expected")
    