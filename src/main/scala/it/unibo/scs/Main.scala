package it.unibo.scs

import akka.actor.typed.{ActorSystem, Behavior}
import it.unibo.scs.car.{Battery, Car, ControlUnit}
import akka.actor.typed.scaladsl.Behaviors
import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor}
import it.unibo.scs.userapp.UserAppActor


enum Commands:
  case Start
  case Stop

import Commands.*

object MainActor:
  def apply(): Behavior[Commands] =
    Behaviors setup { ctx =>
      val controlUnitActors = Set(ctx.spawn(ControlUnit(Car(Battery())), "ControlUnit"))
      val chargingStationActors = (1 to 5).map(id => ctx.spawn(ChargingStationActor(ChargingStation(id)), s"ChargingStation$id")).toSet
      val userAppActor = ctx.spawn(UserAppActor(controlUnitActors, chargingStationActors), "UserApp");
      Behaviors.same
    }

object Main extends App:
  ActorSystem(MainActor(), "Main")

  //todo aggiungere ControlUnit.StartCar() per vedere effettivamente i cambiamenti di stato della colonnina