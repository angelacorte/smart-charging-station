package it.unibo.scs.test.chargingstation

import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor, ChargingStationState, Point2D}
import it.unibo.scs.test.TestService

class ChargingStationTest extends TestService:
  val chargingStation = ChargingStation(id = 0, state = ChargingStationState.FREE, location = Point2D())
  val sender = testKit.spawn(ChargingStationActor(chargingStation))
  val probe = testKit.createTestProbe[ChargingStationActor.SendState]()
  sender ! ChargingStationActor.AskState(chargingStation.id, probe.ref)
  probe.expectMessage(ChargingStationActor.SendState(chargingStation))
