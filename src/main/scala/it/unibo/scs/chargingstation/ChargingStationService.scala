package it.unibo.scs.chargingstation

import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Props}
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import com.typesafe.config.Config
import scala.concurrent.{ExecutionContextExecutor, Future}
import it.unibo.scs.chargingstation.ChargingStationProvider

object ChargingStationService:
  sealed trait Request
  case class Stop() extends Request
  def apply(port: Int, provider: ActorRef[ChargingStationProvider.Request]): Behavior[Request] =
    Behaviors.setup { context =>

      given system: ActorSystem[Nothing] = context.system
      given executionContext: ExecutionContextExecutor = system.executionContext

      val route =
        path("hello") {
          get {
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
