package com.techsylvania.console.app.http

import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.StatusCodes.BadRequest
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import com.techsylvania.console.app.components.SensorInput
import com.techsylvania.console.app.components.basic.BasicService
import com.techsylvania.console.app.components.sensors.SensorService
import com.techsylvania.console.app.http.routes.{BasicRoutes, SensorRoutes}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._

import akka.http.scaladsl.model.StatusCodes.Unauthorized
import akka.http.scaladsl.server.Directives.{complete, extractUri, get, handleExceptions, pathPrefix, post}
import com.techsylvania.console.app.components.sensors.SensorService
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.http.scaladsl.server.Directives._
import StatusCodes._
import com.techsylvania.console.app.components.{Basic, SensorInput}
import com.techsylvania.console.app.components.basic.BasicService
import com.techsylvania.console.app.utils.TechLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._

import scala.collection.immutable
import scala.concurrent.ExecutionContext

class HttpRoute(
               basicService: BasicService,
               sensorService: SensorService
)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport with TechLogging {

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

        } ~ post {
        logger.info("in the post....")
        entity(as[SensorInput]) {
          result => {
            logger.info("found entity and inserting it")
            val r = complete(sensorService.insert(result))
            logger.info("returning after insert")
            r
          }
        } ~
          complete(BadRequest -> "the entity is not corresponding (not a sensor input entity)")
      }
    }

}
