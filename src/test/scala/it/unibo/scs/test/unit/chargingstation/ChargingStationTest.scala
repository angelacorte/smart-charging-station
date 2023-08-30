package it.unibo.scs.test.unit.chargingstation

import it.unibo.scs.cluster.chargingstation.{ChargingStationActor, ChargingStationEvents}
import it.unibo.scs.model.chargerequest.ChargeRequest
import it.unibo.scs.model.chargingstation.{ChargingStation, ChargingStationState, Position}
import it.unibo.scs.model.reservation.Reservation
import it.unibo.scs.test.TestService


class ChargingStationTest extends TestService:
  "A charging station" should {

    val chargingStation = ChargingStation(0, "Enel", "X Charging Station", 100, Position(44.17457186518429, 12.23658150624628))
    val chargeRequest = ChargeRequest(0, 0)
    val reservation = Reservation(0, 0)

    val sender = testKit.spawn(ChargingStationActor(chargingStation))


    "be created" in {
      val probe = testKit.createTestProbe[ChargingStationEvents.Response]()
      sender ! ChargingStationEvents.AskState(probe.ref)
      probe.expectMessage(ChargingStationEvents.ChargingStationUpdated(chargingStation, sender.ref))
    }

    "accept charging request if free" in {
      val probe = testKit.createTestProbe[ChargingStationEvents.ChargeRequestResult]()
      sender ! ChargingStationEvents.Charge(chargeRequest, probe.ref)
      probe.expectMessage(ChargingStationEvents.ChargeRequestOk())
    }

    "not accept charging request if not free" in {
      val probe = testKit.createTestProbe[ChargingStationEvents.ChargeRequestResult]()
      sender ! ChargingStationEvents.Charge(chargeRequest, probe.ref)
      probe.expectMessage(ChargingStationEvents.ChargeRequestNotOk("This charging station is busy"))
    }


    "accept reserve request if free" in {
      val probe = testKit.createTestProbe[ChargingStationEvents.ReservationResult]()
      sender ! ChargingStationEvents.StopCharge()
      sender ! ChargingStationEvents.Reserve(reservation, probe.ref)
      probe.expectMessage(ChargingStationEvents.ReservationOk())
    }

    "not accept reserve request if not free" in {
      val probe = testKit.createTestProbe[ChargingStationEvents.ReservationResult]()
      sender ! ChargingStationEvents.Reserve(Reservation(1,0), probe.ref)
      probe.expectMessage(ChargingStationEvents.ReservationNotOk("This charging station is already reserved"))
    }
  }



