package it.unibo.scs.service.chargingstation

import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.AskPattern.Askable
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import it.unibo.scs.CborSerializable
import it.unibo.scs.cluster.chargingstation.ChargingStationActor.ChargingStationServiceKey
import it.unibo.scs.cluster.chargingstation.ChargingStationEvents
import it.unibo.scs.cluster.chargingstation.ChargingStationEvents.{Charge, ReservationNotOk, Reserve}
import it.unibo.scs.model.chargerequest.ChargeRequest
import it.unibo.scs.model.chargingstation.ChargingStation
import it.unibo.scs.model.chargingstation.ChargingStation.*
import it.unibo.scs.model.reservation.Reservation

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

object ChargingStationProvider:
  private type ChargingStationRegistry = Map[ActorRef[ChargingStationEvents.Request], ChargingStation]
  sealed trait Request
  case class AskAllChargingStations(replyTo: ActorRef[Set[ChargingStation]]) extends Request with CborSerializable
  case class AskChargingStation(id: Int, replyTo: ActorRef[Option[ChargingStation]]) extends Request with CborSerializable
  case class AskToReserveChargingStation(reservation: Reservation, replyTo: ActorRef[ChargingStationEvents.ReservationResult]) extends Request with CborSerializable
  case class AskChargingStationToCharge(request: ChargeRequest, replyTo: ActorRef[ChargingStationEvents.ChargeRequestResult]) extends Request
  case class UpdateChargingStation(chargingStation: ChargingStation, ref: ActorRef[ChargingStationEvents.Request]) extends Request with CborSerializable
  private case class ChargingStationsUpdated(chargingStations: Set[ActorRef[ChargingStationEvents.Request]]) extends Request
  private case class BadRequest() extends Request


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
        // TODO refactor with ask pattern
        val csAdapter = ctx.messageAdapter[ChargingStationEvents.Response] {
          case ChargingStationEvents.ChargingStationUpdated(chargingStation, ref) =>
            UpdateChargingStation(chargingStation, ref)
          case _ =>
            BadRequest()
        }
        refs foreach {
          _ ! ChargingStationEvents.AskState(csAdapter)
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
                replyTo ! value.asInstanceOf[ChargingStationEvents.ReservationResult]
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
                replyTo ! value.asInstanceOf[ChargingStationEvents.ChargeRequestResult]
              case Failure(exception) =>
                replyTo ! ChargingStationEvents.ChargeRequestNotOk(exception.getMessage)
            }
          case None =>
            replyTo ! ChargingStationEvents.ChargeRequestNotOk("Charging station not found")
        }

        running(chargingStations)
      case _ =>
        running(chargingStations)
    }