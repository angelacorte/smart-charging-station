package it.unibo.scs.chargingstation

import it.unibo.scs.CborSerializable
import it.unibo.scs.chargingstation.ChargingStationState.ChargingStationState

object ChargingStationState extends Enumeration:
  type ChargingStationState = Value
  val FREE, CHARGING, RESERVED, UNAVAILABLE = Value

case class ChargingStation(id: Int, state: ChargingStationState = ChargingStationState.FREE) extends CborSerializable