package it.unibo.scs.chargingstation

import scala.util.Random

enum ChargingStationState:
  case FREE
  case CHARGING
  case RESERVED
  case UNAVAILABLE //if the charging station has problems

case class ChargingStation(id: Int, state: ChargingStationState, location: Point2D):
  def changeState(newState: ChargingStationState): ChargingStation = ChargingStation(id, newState, location)
  
case class Point2D(var lat: Int = 0, var lon: Int = 0):
  def createRandom(tx: Int, ty: Int, bx: Int, by: Int): Point2D =
    val rand = new Random()
    if tx <= ty then
      lat = rand.between(tx, ty + 1)
    else
      lat = rand.between(ty, tx + 1)
    if bx <= by then
      lon = rand.between(bx, by + 1)
    else
      lon = rand.between(by, bx + 1)
    Point2D(lat, lon)