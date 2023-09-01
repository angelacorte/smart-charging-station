package it.unibo.scs.test.unit.chargingstation

import it.unibo.scs.cluster.chargingstation.{ChargingStationActor, ChargingStationEvents}
import it.unibo.scs.model.chargerequest.{ChargeRequest, ChargeRequestNotOk, ChargeRequestOk, ChargeRequestResult}
import it.unibo.scs.model.chargingstation.{ChargingStation, ChargingStationState, Position}
import it.unibo.scs.model.reservation.{Reservation, ReservationNotOk, ReservationOk, ReservationResult}
import it.unibo.scs.service.chargingstation.ChargingStationProvider
import it.unibo.scs.service.chargingstation.ChargingStationProvider.UpdateChargingStation
import it.unibo.scs.test.TestService


class ChargingStationTest extends TestService:
  "A charging station" should {

    val chargingStation = ChargingStation(0, "Enel", "X Charging Station", 100, Position(44.17457186518429, 12.23658150624628))
    val chargeRequest = ChargeRequest("6d", 0)
    val reservation = Reservation("6d", 0)

    val sender = testKit.spawn(ChargingStationActor(chargingStation))


    "be created" in {
      val probe = testKit.createTestProbe[ChargingStationProvider.Request]()
      sender ! ChargingStationEvents.AskState(probe.ref)
      probe.expectMessage(UpdateChargingStation(chargingStation, sender.ref))
    }

    "accept charging request if free" in {
      val probe = testKit.createTestProbe[ChargeRequestResult]()
      sender ! ChargingStationEvents.Charge(chargeRequest, probe.ref)
      probe.expectMessage(ChargeRequestOk())
    }

    "not accept charging request if not free" in {
      val probe = testKit.createTestProbe[ChargeRequestResult]()
      sender ! ChargingStationEvents.Charge(chargeRequest, probe.ref)
      probe.expectMessage(ChargeRequestNotOk("This charging station is busy"))
    }


    "accept reserve request if free" in {
      val probe = testKit.createTestProbe[ReservationResult]()
      sender ! ChargingStationEvents.StopCharge()
      sender ! ChargingStationEvents.Reserve(reservation, probe.ref)
      probe.expectMessage(ReservationOk())
    }

    "not accept reserve request if not free" in {
      val probe = testKit.createTestProbe[ReservationResult]()
      sender ! ChargingStationEvents.Reserve(Reservation("6d",0), probe.ref)
      probe.expectMessage(ReservationNotOk("This charging station is already reserved"))
    }
  }



