name := "techsylvania"

version := "0.1"

scalaVersion := "2.12.6"

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths.get

import scala.collection.JavaConversions._
import complete.DefaultParsers._

import scala.collection.mutable
import scala.util.Try

lazy val akkaHttpVersion = "10.0.10"
lazy val scalaTestV      = "3.0.4"
lazy val akkaVersion     = "2.4.19"
lazy val slickVersion    = "3.2.1"
lazy val circeV          = "0.9.0"
lazy val sttpV           = "1.0.0"
val tsecV                 = "0.0.1-M9"

resolvers += "jmcardon at bintray" at "https://dl.bintray.com/jmcardon/tsec"


lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.busymachines",
      scalaVersion    := "2.12.3"
    )),
    name := "HealthSNAP-Backend",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,

      // Support of CORS requests, version depends on akka-http
      "ch.megard" %% "akka-http-cors" % "0.2.2",

      // SQL generator
      "com.typesafe.slick" %% "slick" % slickVersion,

      // Postgres driver
      "org.postgresql" % "postgresql" % "42.1.4",

      // Migration for SQL databases
      "org.flywaydb" % "flyway-core" % "4.2.0",

      // Connection pool for database
      "com.zaxxer" % "HikariCP" % "2.7.0",

      // Encoding decoding sugar, used in passwords hashing
      "com.roundeights" %% "hasher" % "1.2.0",

      // Config file parser
      "com.github.pureconfig" %% "pureconfig" % "0.8.0",

      // JSON serialization library
      "io.circe" %% "circe-core" % circeV,
      "io.circe" %% "circe-generic" % circeV,
      "io.circe" %% "circe-parser" % circeV,

      //CSV writer
      "au.com.bytecode" % "opencsv" % "2.4",

      // Sugar for serialization and deserialization in akka-http with circe
      "de.heikoseeberger" %% "akka-http-circe" % "1.19.0",

      // Validation library
      "com.wix" %% "accord-core" % "0.7.1",

      // Log4J
      "org.slf4j" % "slf4j-api" % "1.7.25",
      "org.slf4j" % "slf4j-log4j12" % "1.7.25",

      // Mail
      //"javax.mail" % "mail" % "1.4",
      "com.sun.mail" % "javax.mail" % "1.5.5",

      //Crypto
      "io.github.jmcardon" %% "tsec-common" % tsecV,
      "io.github.jmcardon" %% "tsec-password" % tsecV,
      "io.github.jmcardon" %% "tsec-mac" % tsecV,
      "io.github.jmcardon" %% "tsec-jwt-mac" % tsecV,



      //TEST
      // Http client, used currently only for IT test
      "com.softwaremill.sttp" %% "core"              % sttpV % Test,
      "com.softwaremill.sttp" %% "akka-http-backend" % sttpV % Test,

      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"            % scalaTestV      % Test,

      "ru.yandex.qatools.embed" % "postgresql-embedded"   % "2.4"   % Test,
      "org.mockito"             % "mockito-all"           % "1.9.5" % Test,

      "org.jvnet.mock-javamail" % "mock-javamail" % "1.9" % "test",

      "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.14.0"

    )
  )
