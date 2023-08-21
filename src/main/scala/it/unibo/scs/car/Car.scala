package it.unibo.scs.car

trait Car:
  val charge: Double
  val maxCharge = 1
  
object Car:
  def apply(): Car = CarImpl(1)
  def apply(charge: Double): Car = CarImpl(charge)
  private class CarImpl(val charge: Double) extends Car
  
  
