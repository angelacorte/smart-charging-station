package it.unibo.scs

import akka.actor.typed.{ActorSystem, Behavior}
import com.typesafe.config.ConfigFactory
import it.unibo.scs.model.chargingstation.ChargingStation.*
import it.unibo.scs.cluster.chargingstation.ChargingStationActor
import it.unibo.scs.model.chargingstation
import it.unibo.scs.model.chargingstation.{ChargingStation, Position}
import it.unibo.scs.service.chargingstation.ChargingStationProvider

object App:
  private def startup[T](port: Int)(root: => Behavior[T]): Unit =
    val config = ConfigFactory.parseString(s"""
             akka.remote.artery.canonical.port=$port
             """)
      .withFallback(ConfigFactory.load("smart-charging-station"))
    ActorSystem[T](root, "ClusterSystem", config)

  def main(args: Array[String]): Unit =
    var port = 25251
    val chargingStations: List[ChargingStation] = List(
      chargingstation.ChargingStation(0, "Enel", "X Charging Station", 100, Position(44.17457186518429, 12.23658150624628)),
      chargingstation.ChargingStation(1, "Enel", "X WAY Charging Station", 100, Position(44.14523265977938, 12.260617176277155)),
      chargingstation.ChargingStation(2, "Enel", "X WAY Charging Station", 100, Position(44.14506131431948, 12.25948270076121)),
      chargingstation.ChargingStation(3, "Enel", "X Charging Station", 100, Position(44.145612588232495, 12.238379768214596)),
      chargingstation.ChargingStation(4, "Neogy", "Charging Station", 100, Position(44.145554034484775, 12.226701099558664)),
      chargingstation.ChargingStation(5, "Enel", "X Charging Station", 100, Position(44.139899361053246, 12.240021789211541)),
      chargingstation.ChargingStation(6, "Eway", "Charging Station", 100, Position(44.14672422842581, 12.218455633179834)),
      chargingstation.ChargingStation(7, "E.CO", "Charging Station", 100, Position(44.136807640165884, 12.22721036271666)),
      chargingstation.ChargingStation(8, "Enel", "X Charging Station", 100, Position(44.13775592395241, 12.241067840087673)),
      chargingstation.ChargingStation(9, "Be charge", "X Charging Station", 100, Position(44.13716960989673, 12.269283127921085)),
    )
    startup(port)(ChargingStationProvider())
    chargingStations foreach { cs =>
      port += 1
      startup(port)(ChargingStationActor(cs))
    }