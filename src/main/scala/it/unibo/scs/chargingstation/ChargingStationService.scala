package it.unibo.scs.chargingstation

import akka.actor.typed.scaladsl.AskPattern.*
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*

import akka.util.Timeout
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import it.unibo.scs.chargingstation.ChargingStation.*
import it.unibo.scs.chargingstation.ChargingStationProvider.GetChargingStations

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt

object ChargingStationService:
  sealed trait Request
  case class Stop() extends Request


  def apply(provider: ActorRef[ChargingStationProvider.Request], port: Int = 8080): Behavior[Request] =
    Behaviors.setup { context =>

      given system: ActorSystem[Nothing] = context.system
      given executionContext: ExecutionContextExecutor = system.executionContext
      import Formats.given // for the implicit marshaller

      val route =
        path("chargingstations") {
          get {
            given timeout: Timeout = 5.seconds

            val chargingStations = provider.ask(GetChargingStations)
            onSuccess(chargingStations) { stations =>
              complete(stations.toList)
            }
          }
        }

      val bindingFuture = Http().newServerAt("localhost", port).bind(route)
      context.log.info(s"Server online at http://localhost:$port/")

      Behaviors.receiveMessage {
        case Stop() =>
          context.log.info("Stopping ChargingStationService")
          bindingFuture
            .flatMap(_.unbind()) // trigger unbinding from the port
            .onComplete(_ => system.terminate()) // and shutdown when done
          Behaviors.stopped
        case _ =>
          Behaviors.same
      }
    }
