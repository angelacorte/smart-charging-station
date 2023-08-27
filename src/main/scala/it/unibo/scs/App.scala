package it.unibo.scs

import akka.actor.typed.{ActorSystem, Behavior}
import com.typesafe.config.ConfigFactory
import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor, ChargingStationProvider, ChargingStationService}

object App:
  def startup[T](port: Int)(root: => Behavior[T]): Unit =
    val config = ConfigFactory.parseString(s"""
             akka.remote.artery.canonical.port=$port
             """)
      .withFallback(ConfigFactory.load("smart-charging-station"))
    ActorSystem[T](root, "ClusterSystem", config)

  def main(args: Array[String]): Unit =
    var port = 25251
    startup(port)(ChargingStationProvider())
    (0 to 3) foreach { i =>
      port += 1
      startup(port)(ChargingStationActor(ChargingStation(i)))
    }
    //startup(port + 1)(ChargingStationService())