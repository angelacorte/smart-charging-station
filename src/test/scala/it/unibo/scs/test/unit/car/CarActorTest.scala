package it.unibo.scs.test.unit.car

import it.unibo.scs.car.{Car, ControlUnit}
import it.unibo.scs.test.TestService

class CarActorTest extends TestService:
  "A Car" should {
    val car = Car()
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