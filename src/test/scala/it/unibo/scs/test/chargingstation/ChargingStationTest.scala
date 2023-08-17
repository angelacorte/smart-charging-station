package it.unibo.scs.test.chargingstation

import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor, ChargingStationState, Point2D}
import it.unibo.scs.test.TestService

class ChargingStationTest extends TestService:
  val chargingStation = ChargingStation(id = 0, state = ChargingStationState.FREE, location = Point2D())
  val sender = testKit.spawn(ChargingStationActor(chargingStation))
  val probe = testKit.createTestProbe[ChargingStationActor.UpdateChargingStation]()
  sender ! ChargingStationActor.SendState(chargingStation.id, probe.ref)
  probe.expectMessage(ChargingStationActor.UpdateChargingStation(chargingStation))

  val newState = ChargingStationState.CHARGING
  sender ! ChargingStationActor.ChangeState(csID = chargingStation.id, newState = newState, probe.ref)
  probe.expectMessage(ChargingStationActor.UpdateChargingStation(chargingStation.copy(state = newState)))
