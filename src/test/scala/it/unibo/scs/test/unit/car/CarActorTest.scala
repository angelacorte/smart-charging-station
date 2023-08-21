package it.unibo.scs.test.unit.car

import it.unibo.scs.car.{Car, CarActor}
import it.unibo.scs.test.TestService

class CarActorTest extends TestService:
  "A Car" should {
    val car = Car()
    val sender = testKit.spawn(CarActor(car))
    val probe = testKit.createTestProbe[CarActor.Response]()

    "Communicate its state" in {
      sender ! CarActor.AskState(probe.ref)
      probe.expectMessage(CarActor.CarUpdated(car))
    }

    "Start" in {
      sender ! CarActor.StartCar()
      probe.expectNoMessage()
    }
  }