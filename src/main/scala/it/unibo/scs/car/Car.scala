package it.unibo.scs.car

case class Car(charge: Double)

object Car:
  val maxCharge = 1
  def apply(): Car = Car(Car.maxCharge)
