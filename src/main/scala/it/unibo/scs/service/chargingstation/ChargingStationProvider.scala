package it.unibo.scs.service.chargingstation

import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.AskPattern.Askable
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import it.unibo.scs.CborSerializable
import it.unibo.scs.cluster.chargingstation.ChargingStationActor.ChargingStationServiceKey
import it.unibo.scs.cluster.chargingstation.ChargingStationEvents
import it.unibo.scs.cluster.chargingstation.ChargingStationEvents.{Charge, Reserve}
import it.unibo.scs.model.chargerequest.{ChargeRequest, ChargeRequestNotOk, ChargeRequestResult, ChargeRequestOk}
import it.unibo.scs.model.chargingstation.ChargingStation
import it.unibo.scs.model.chargingstation.ChargingStation.*
import it.unibo.scs.model.reservation.{Reservation, ReservationNotOk, ReservationResult, ReservationOk}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

/**
 * This actor is responsible for managing the charging stations. I does so by observing [[ChargingStationActor]] and by exposing a [[ChargingStationService]]
 */
object ChargingStationProvider:
  private type ChargingStationRegistry = Map[ActorRef[ChargingStationEvents.Request], ChargingStation]

  /**
   * This trait represents the messages that can be sent to the [[ChargingStationProvider]].
   */
  sealed trait Request

  /**
   * This message is sent to the [[ChargingStationProvider]] to ask for a set of all the charging stations.
   * @param replyTo the actor that will receive the set of charging stations
   */
  case class AskAllChargingStations(replyTo: ActorRef[Set[ChargingStation]]) extends Request with CborSerializable

  /**
   * This message is sent to the [[ChargingStationProvider]] to ask for a specific charging station.
   * @param id the id of the charging station
   * @param replyTo the actor that will receive the charging station
   */
  case class AskChargingStation(id: Int, replyTo: ActorRef[Option[ChargingStation]]) extends Request with CborSerializable

  /**
   * This message is sent to the [[ChargingStationProvider]] to ask for a reservation of a charging station.
   * @param reservation the reservation request
   * @param replyTo the actor that will receive the reservation result
   */
  case class AskToReserveChargingStation(reservation: Reservation, replyTo: ActorRef[ReservationResult]) extends Request with CborSerializable

  /**
   * This message is sent to the [[ChargingStationProvider]] to ask a charging station to begin the charging process.
   * @param request the charge request
   * @param replyTo the actor that will receive the charge request result
   */
  case class AskChargingStationToCharge(request: ChargeRequest, replyTo: ActorRef[ChargeRequestResult]) extends Request

  /**
   * This message is sent to the [[ChargingStationProvider]] to update the charging station inside its [[ChargingStationRegistry]].
   * @param chargingStation the charging station to update
   * @param ref the actor that represents the charging station in the cluster
   */
  case class UpdateChargingStation(chargingStation: ChargingStation, ref: ActorRef[ChargingStationEvents.Request]) extends Request with CborSerializable

  /**
   * This message is sent to the [[ChargingStationProvider]] when the [[Receptionist]] has updated the charging stations set in the cluster.
   * @param chargingStations the set of charging stations
   */
  private case class ChargingStationsUpdated(chargingStations: Set[ActorRef[ChargingStationEvents.Request]]) extends Request
  
  /**
   * This message represents an invalid request.
   */
  private case class BadRequest() extends Request

  /**
   * This is the [[ServiceKey]] used to register the [[ChargingStationProvider]] in the [[Receptionist]].
   */
  val ProviderServiceKey: ServiceKey[Request] = ServiceKey("ChargingStationProvider")

  def apply(chargingStations: ChargingStationRegistry = Map.empty): Behavior[Request] =
    Behaviors setup { ctx =>
      val subscriptionAdapter = ctx.messageAdapter[Receptionist.Listing]{
        case ChargingStationServiceKey.Listing(refs) =>
          ChargingStationsUpdated(refs)
      }
      ctx.system.receptionist ! Receptionist.Subscribe(ChargingStationServiceKey, subscriptionAdapter)
      ctx.system.receptionist ! Receptionist.Register(ProviderServiceKey, ctx.self)

      /** SPAWN CS SERVICE */
      ctx.spawn(ChargingStationService(ctx.self), "ChargingStationService")

      given timeout: akka.util.Timeout = 5.seconds
      given executionContext: ExecutionContextExecutor = ctx.system.executionContext
      given scheduler: akka.actor.typed.Scheduler = ctx.system.scheduler

      running(chargingStations)
    }

  private def running(
                       chargingStations: ChargingStationRegistry = Map.empty,
                     )
                      (using
                        timeout: akka.util.Timeout,
                        executionContext: ExecutionContextExecutor,
                        scheduler: akka.actor.typed.Scheduler,
                      ): Behavior[Request] =

    def getEntry(chargingStationId: Int): Option[(ActorRef[ChargingStationEvents.Request], ChargingStation)] =
      chargingStations.find(_._2.id == chargingStationId)

    Behaviors receive {
      case (ctx, ChargingStationsUpdated(refs)) =>
        refs foreach {
          _ ! ChargingStationEvents.AskState(ctx.self)
        }
        running(chargingStations)
      case (_, UpdateChargingStation(chargingStation, ref)) =>
        running(chargingStations.updated(ref, chargingStation))
      case (_, AskAllChargingStations(replyTo)) =>
        replyTo ! chargingStations.values.toSet
        running(chargingStations)
      case (_, AskChargingStation(id, replyTo)) =>
        replyTo ! chargingStations.values.find(_.id == id)
        running(chargingStations)
      case (_, AskToReserveChargingStation(r, replyTo)) =>
        getEntry(r.chargingStationId) match {
          case Some((ref, _)) =>
            val reservation = ref.ask(Reserve(r, _))
            reservation.onComplete {
              case Success(value) =>
                replyTo ! value.asInstanceOf[ReservationResult]
              case Failure(exception) =>
                replyTo ! ReservationNotOk(exception.getMessage)
            }
          case None =>
            replyTo ! ReservationNotOk("Charging station not found")
        }
        running(chargingStations)
      case (_, AskChargingStationToCharge(r, replyTo)) =>
        getEntry(r.chargingStationId) match {
          case Some((ref, _)) =>
            val chargeRequest = ref.ask(Charge(r, _))
            chargeRequest.onComplete {
              case Success(value) =>
                replyTo ! value.asInstanceOf[ChargeRequestResult]
              case Failure(exception) =>
                replyTo ! ChargeRequestNotOk(exception.getMessage)
            }
          case None =>
            replyTo ! ChargeRequestNotOk("Charging station not found")
        }
        running(chargingStations)
      case _ =>
        running(chargingStations)
    }