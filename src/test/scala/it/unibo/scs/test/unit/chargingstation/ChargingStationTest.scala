package it.unibo.scs.test.unit.chargingstation

import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor, ChargingStationEvents, ChargingStationState}
import it.unibo.scs.test.TestService
import it.unibo.scs.car.{Car, ControlUnit}
import it.unibo.scs.chargingstation.ChargingStationEvents.SendChargeFromChargingStation
import it.unibo.scs.chargingstation.ChargingStationState.FREE


class ChargingStationTest extends TestService:
  "A charging station" should {

    val chargingStation = ChargingStation(id = 0)
    val sender = testKit.spawn(ChargingStationActor(chargingStation))
    val probe = testKit.createTestProbe[ChargingStationEvents.Response]()

    "be created" in {
      sender ! ChargingStationEvents.AskState(probe.ref)
      probe.expectMessage(ChargingStationEvents.ChargingStationUpdated(chargingStation, sender.ref))
    }

    "accept charging request if free" in {
      sender ! ChargingStationEvents.Charge(probe.ref)
      probe.expectMessage(ChargingStationEvents.Ok())
      probe.expectMessage(SendChargeFromChargingStation(5.0, sender.ref))
    }

    "not accept charging request if not free" in {
      //inviare qualcosa di diverso da charge cosi cambia lo stato
      sender ! ChargingStationEvents.Charge(probe.ref)
      probe.expectMessage(ChargingStationEvents.NotOk(ChargingStationState.CHARGING))
    }

    "stop the charge" in {
      sender ! ChargingStationEvents.StopCharge()
      probe.expectNoMessage()
    }

    "accept reserve request if free" in {
      sender ! ChargingStationEvents.Reserve(probe.ref)
      probe.expectMessage(ChargingStationEvents.Ok())
    }

    "not accept reserve request if not free" in {
      sender ! ChargingStationEvents.Charge(probe.ref)
      probe.expectMessage(ChargingStationEvents.NotOk(ChargingStationState.RESERVED))
    }
  }



