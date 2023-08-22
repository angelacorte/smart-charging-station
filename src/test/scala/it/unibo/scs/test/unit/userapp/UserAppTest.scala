package it.unibo.scs.test.unit.userapp

import akka.actor.testkit.typed.javadsl.LoggingTestKit
import it.unibo.scs.car.{Battery, Car, ControlUnit}
import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor}
import it.unibo.scs.test.TestService
import it.unibo.scs.userapp.UserAppEvents.Tick
import it.unibo.scs.userapp.{UserApp, UserAppActor, UserAppEvents}

class UserAppTest extends TestService:
  "A user app" should {
    val controlUnitActors = Set(testKit.spawn(ControlUnit(Car(Battery()))))
    val chargingStationsActors = (1 to 5).map(id => testKit.spawn(ChargingStationActor(ChargingStation(id)))).toSet
    val sender = testKit.spawn(UserAppActor(controlUnitActors, chargingStationsActors))
    val probe = testKit.createTestProbe[UserAppEvents.Response]()

    "do something" in {
      ???
    }
  }
