package it.unibo.scs.test.chargingstation

import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor, ChargingStationEvents, ChargingStationState, Point2D}
import it.unibo.scs.test.TestService

class ChargingStationTest extends TestService:
  "A charging station" should {
    "be created" in {
      val chargingStation = ChargingStation(id = 0, state = ChargingStationState.FREE, location = Point2D())
      val sender = testKit.spawn(ChargingStationActor(chargingStation))
      val probe = testKit.createTestProbe[ChargingStationEvents.ChargingStationListener]()
      sender ! ChargingStationEvents.AskState(probe.ref)
      probe.expectMessage(ChargingStationEvents.ChargingStationUpdated(chargingStation))
    }
  }


