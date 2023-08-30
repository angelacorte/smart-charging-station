package it.unibo.scs.service.chargingstation

import akka.actor.typed.scaladsl.AskPattern.*
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport.*
import akka.http.scaladsl.server.Directives.*
import akka.util.Timeout
import it.unibo.scs.cluster.chargingstation.ChargingStationEvents
import it.unibo.scs.cluster.chargingstation.ChargingStationEvents.{ReservationNotOk, ReservationOk}
import it.unibo.scs.model.chargingstation.{ChargingStation, Reservation}
import it.unibo.scs.model.chargingstation.ChargingStation.*
import it.unibo.scs.service.chargingstation.ChargingStationProvider.{AskAllChargingStations, AskChargingStation, AskToReserveChargingStation}
import it.unibo.scs.service.cors.CORSHandler.corsHandler

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt


object ChargingStationService:
  sealed trait Request
  case class Stop() extends Request


  def apply(provider: ActorRef[ChargingStationProvider.Request], port: Int = 8080): Behavior[Request] =
    Behaviors.setup { context =>

      /** SETUP IMPLICITS */
      given system: ActorSystem[Nothing] = context.system
      given executionContext: ExecutionContextExecutor = system.executionContext // for Future.flatmap
      given timeout: Timeout = 5.seconds // for the ask pattern
      import ChargingStation.Formats.given // for the implicit marshaller
      import Reservation.Formats.given // for the implicit marshaller

      /** SETUP ROUTE */
      val route = corsHandler(
        concat(
          path("chargingstations") {
            get {
              val chargingStations = provider.ask(AskAllChargingStations)
              onSuccess(chargingStations) { stations =>
                complete(stations.toList)
              }
            }
          },
          path("chargingstations" / IntNumber) { id =>
            get {
              val chargingStation = provider.ask(AskChargingStation(id, _))
              onSuccess(chargingStation) { station =>
                // necessary cast because the compiler is an idiot (or because of type erasure)
                station.asInstanceOf[Option[ChargingStation]] match
                  case Some(station) => complete(station)
                  case None => complete("ChargingStation not found")
              }
            }
          },
          path( "reserve-station") {
            post {
              entity(as[Reservation]) { reservation =>
                val response = provider.ask(AskToReserveChargingStation(reservation, _))
                onSuccess(response) { res =>
                  res.asInstanceOf[ChargingStationEvents.ReservationResult] match
                    case ReservationOk() => complete("Reservation successful")
                    case ReservationNotOk(reason) => complete(s"Reservation not successful, reason: $reason")
                }
              }
            }
          }
        )
      )


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
