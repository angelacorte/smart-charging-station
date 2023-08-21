package it.unibo.scs.test.unit.userapp

import it.unibo.scs.test.TestService
import it.unibo.scs.userapp.{UserApp, UserAppActor, UserAppEvents}

class ChargingStationTest extends TestService:
  "A user app" should {
    val userapp = UserApp(0)
    val sender = testKit.spawn(UserAppActor(userapp))
    val probe = testKit.createTestProbe[UserAppEvents.Response]()

    "do something" in {
      ???
    }
  }
