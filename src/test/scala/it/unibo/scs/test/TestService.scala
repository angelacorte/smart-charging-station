package it.unibo.scs.test

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.ActorSystem
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TestService extends AnyWordSpec with BeforeAndAfterAll with Matchers:
  val testKit = ActorTestKit()
  given system: ActorSystem[Nothing] = testKit.system
  override def afterAll(): Unit = testKit.shutdownTestKit()
