package it.unibo.scs.model.chargingstation

import it.unibo.scs.CborSerializable
import it.unibo.scs.model.chargingstation.{ChargingStation, ChargingStationState, Position}
import it.unibo.scs.model.chargingstation.ChargingStationState.ChargingStationState
import spray.json.DefaultJsonProtocol.*
import spray.json.{DeserializationException, JsArray, JsString, JsValue, RootJsonFormat, enrichAny}

object ChargingStationState extends Enumeration with Serializable:
  type ChargingStationState = Value
  val FREE, CHARGING, RESERVED, UNAVAILABLE = Value

case class Position(latitude: Double, longitude: Double) extends CborSerializable
case class ChargingStation(id: Int, provider: String, name: String, voltage: Double, position: Position = Position(0,0), state: ChargingStationState = ChargingStationState.FREE) extends CborSerializable

object ChargingStation:
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
    