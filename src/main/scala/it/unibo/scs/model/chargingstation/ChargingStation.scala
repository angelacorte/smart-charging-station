package it.unibo.scs.model.chargingstation

import it.unibo.scs.CborSerializable
import it.unibo.scs.model.chargingstation.ChargingStationState.ChargingStationState

/**
 * The state of a charging station.
 * A charging station can be in one of the following states:
 * - FREE: the charging station is free and can be used by a vehicle
 * - CHARGING: the charging station is currently charging a vehicle
 * - RESERVED: the charging station is reserved for a vehicle
 * - UNAVAILABLE: the charging station is not available
 */
object ChargingStationState extends Enumeration with Serializable:
  type ChargingStationState = Value
  val FREE, CHARGING, RESERVED, UNAVAILABLE = Value

/**
 * A position in the world.
 * @param latitude the latitude of the position
 * @param longitude the longitude of the position
 */
case class Position(latitude: Double, longitude: Double) extends CborSerializable

/**
 * A charging station.
 * @param id the id of the charging station
 * @param provider the provider of the charging station
 * @param name the name of the charging station
 * @param voltage the voltage of the charging station
 * @param position the position of the charging station
 * @param state the current state of the charging station
 */
case class ChargingStation(id: Int, provider: String, name: String, voltage: Double, position: Position = Position(0,0), state: ChargingStationState = ChargingStationState.FREE) extends CborSerializable

object ChargingStation:
  /**
   * Implicit class to add some useful methods to the ChargingStation class.
   * */
  extension (station: ChargingStation)

    /**
     * Check if the charging station is free.
     * @return true if the charging station is free, false otherwise
     */
    def isFree: Boolean = station.state == ChargingStationState.FREE
    /**
     * Check if the charging station is charging a vehicle.
     * @return true if the charging station is charging a vehicle, false otherwise
     */
    def isCharging: Boolean = station.state == ChargingStationState.CHARGING
    /**
     * Check if the charging station is reserved for a vehicle.
     * @return true if the charging station is reserved for a vehicle, false otherwise
     */
    def isReserved: Boolean = station.state == ChargingStationState.RESERVED
    /**
     * Check if the charging station is unavailable.
     * @return true if the charging station is unavailable, false otherwise
     */
    def isUnavailable: Boolean = station.state == ChargingStationState.UNAVAILABLE
    /**
     * @return the charging station in the FREE state
     */
    def toFree: ChargingStation = station.copy(state = ChargingStationState.FREE)
    /**
     * @return the charging station in the CHARGING state
     */
    def toCharging: ChargingStation = station.copy(state = ChargingStationState.CHARGING)
    /**
     * @return the charging station in the UNAVAILABLE state
     */
    def toReserved: ChargingStation = station.copy(state = ChargingStationState.RESERVED)