package it.unibo.scs.test.unit.car

import it.unibo.scs.car.{Battery, Car, ControlUnit}
import it.unibo.scs.test.TestService

class ControlUnitTest extends TestService:
  "A Car" should {
    val car = Car(Battery())
    val sender = testKit.spawn(ControlUnit(car))
    val probe = testKit.createTestProbe[ControlUnit.Response]()

    "Communicate its state" in {
      sender ! ControlUnit.AskState(probe.ref)
      probe.expectMessage(ControlUnit.CarUpdated(car))
    }

    "Start" in {
      sender ! ControlUnit.StartCar()
      probe.expectNoMessage()
    }
  }