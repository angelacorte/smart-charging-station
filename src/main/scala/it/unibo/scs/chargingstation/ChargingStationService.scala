package it.unibo.scs.chargingstation

import akka.actor.typed.scaladsl.AskPattern.*
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import akka.util.Timeout
import it.unibo.scs.chargingstation.ChargingStationProvider.GetChargingStations

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt

object ChargingStationService:
  sealed trait Request
  case class Stop() extends Request
  private case class ProvidersUpdated(providers: Set[ActorRef[ChargingStationProvider.Request]]) extends Request
  
  def apply(provider: ActorRef[ChargingStationProvider.Request], port: Int = 8080): Behavior[Request] =
    Behaviors.setup { context =>

      given system: ActorSystem[Nothing] = context.system
      given executionContext: ExecutionContextExecutor = system.executionContext

      val route =
        path("hello") {
          get {
            given timeout: Timeout = 5.seconds
              for
                s <- provider.ask(GetChargingStations(_))
                _ = println(s)
              yield s
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
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
