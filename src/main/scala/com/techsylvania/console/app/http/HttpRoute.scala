package com.techsylvania.console.app.http

import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import com.techsylvania.console.app.components.basic.BasicService
import com.techsylvania.console.app.components.sensors.SensorService
import com.techsylvania.console.app.http.routes.{BasicRoutes, SensorRoutes}

import scala.collection.immutable
import scala.concurrent.ExecutionContext

class HttpRoute(
               basicService: BasicService,
               sensorService: SensorService
)(implicit executionContext: ExecutionContext) {

  private val _basicRouter = new BasicRoutes(basicService)
  private val _sensorRouter = new SensorRoutes(sensorService = sensorService)

  val settings = CorsSettings.defaultSettings
    .copy(
      allowedMethods = immutable.Seq(
        GET, PUT, POST, HEAD, OPTIONS, DELETE
      )
    )

  val route: Route =
    cors(settings) {
      pathPrefix("v1") {
        _basicRouter.route ~
        _sensorRouter.route
      } ~
        pathPrefix("healthcheck") {
          get {
            complete("OK")
          }
        }
    }

}
