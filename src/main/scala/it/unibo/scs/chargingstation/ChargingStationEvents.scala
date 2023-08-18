package it.unibo.scs.chargingstation

import akka.actor.typed.ActorRef
import it.unibo.scs.car.CarActor

object ChargingStationEvents:
  sealed trait Event

  sealed trait ChargingStationListener

  case class ChargingStationUpdated(chargingStation: ChargingStation) extends ChargingStationListener

  case class AskState(replyTo: ActorRef[ChargingStationUpdated]) extends Event

  case class Charge(replyTo: ActorRef[ChargingStationListener]) extends Event

  case class Reserve(replyTo: ActorRef[ChargingStationListener]) extends Event

  case class StopCharge() extends Event

  case class Tick() extends ChargingStationListener

  enum Response extends ChargingStationListener:
    case Ok
    case NotOk(state: ChargingStationState)