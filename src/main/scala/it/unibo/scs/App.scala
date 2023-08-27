package it.unibo.scs

import akka.actor.typed.{ActorSystem, Behavior}
import com.typesafe.config.ConfigFactory
import it.unibo.scs.chargingstation.{ChargingStation, ChargingStationActor, ChargingStationProvider, ChargingStationService}

object App:
  def startup[T](port: Int)(root: => Behavior[T]) =
    val config = ConfigFactory.parseString(s"""
             akka.remote.artery.canonical.port=$port
             """)
    ActorSystem[T](root, "ClusterSystem", config)

  def main(args: Array[String]): Unit =
    var port = 25251
    (0 to 3) foreach { i =>
      startup(port)(ChargingStationActor(ChargingStation(i)))
      port += 1
    }
    val provider = startup(port)(ChargingStationProvider())
    ActorSystem[ChargingStationService.Request](ChargingStationService(8080, provider), "ClusterSystem")
    ()