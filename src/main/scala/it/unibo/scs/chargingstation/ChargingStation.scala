package it.unibo.scs.chargingstation

import scala.util.Random

enum ChargingStationState:
  case FREE
  case CHARGING
  case RESERVED
  case UNAVAILABLE //if the charging station has problems

case class ChargingStation(id: Int)