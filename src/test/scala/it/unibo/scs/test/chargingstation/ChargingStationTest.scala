package it.unibo.scs.test.chargingstation

import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor, ChargingStationEvents, ChargingStationState, Point2D}
import it.unibo.scs.test.TestService
import it.unibo.scs.car.{Car, CarActor}


class ChargingStationTest extends TestService:
  "A charging station" should {
    "be created" in {
      val chargingStation = ChargingStation(id = 0, state = ChargingStationState.FREE, location = Point2D())
      val sender = testKit.spawn(ChargingStationActor(chargingStation))
      val probe = testKit.createTestProbe[ChargingStationEvents.Response]()
      sender ! ChargingStationEvents.AskState(probe.ref)
      probe.expectMessage(ChargingStationEvents.ChargingStationUpdated(chargingStation))
    }

    "accept charging request if free" in {
      val chargingStation = ChargingStation(id = 0, state = ChargingStationState.FREE, location = Point2D())
      val sender = testKit.spawn(ChargingStationActor(chargingStation))
      val probe = testKit.createTestProbe[ChargingStationEvents.Response]()
      sender ! ChargingStationEvents.Charge(probe.ref)
      probe.expectMessage(ChargingStationEvents.Ok())
    }

    "not accept charging request if not free" in {
      val chargingStation = ChargingStation(id = 0, state = ChargingStationState.CHARGING, location = Point2D())
      val sender = testKit.spawn(ChargingStationActor(chargingStation))
      val probe = testKit.createTestProbe[ChargingStationEvents.Response]()
      sender ! ChargingStationEvents.Charge(probe.ref)
      probe.expectMessage(ChargingStationEvents.NotOk(chargingStation.state))
    }
  }



