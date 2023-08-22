package it.unibo.scs.car

case class Car(battery: Battery)

case class Battery(currentCharge: Double = 1):
  def charge(amount: Double): Battery = copy(currentCharge = currentCharge + amount)
  def discharge: Battery = copy(currentCharge = currentCharge - 0.1)