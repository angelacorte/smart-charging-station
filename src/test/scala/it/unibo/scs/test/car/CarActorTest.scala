package it.unibo.scs.test.car

import it.unibo.scs.car.{Car, CarActor}
import it.unibo.scs.test.TestService

class CarActorTest extends TestService:
  "A Car" should {
    "be able to charge" in {
      val car = Car(0)
      val sender = testKit.spawn(CarActor(car))
      val probe = testKit.createTestProbe[CarActor.CarUpdated]()
      sender ! CarActor.StartCharge()
      sender ! CarActor.SendCharge(0.1, probe.ref)
      probe.expectMessage(CarActor.CarUpdated(car.copy(charge = 0.1)))
    }
  }