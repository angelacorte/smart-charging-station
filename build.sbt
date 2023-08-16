ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

val AkkaVersion = "2.8.4"

lazy val root = (project in file("."))
  .settings(
    name := "smart-charging-station",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.14" % Test,
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
    ),
  )
