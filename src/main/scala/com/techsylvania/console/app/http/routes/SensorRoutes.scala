package com.techsylvania.console.app.http.routes

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

import scala.concurrent.ExecutionContext

class SensorRoutes(sensorService: SensorService)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport with TechLogging {

  val route: Route =

    pathPrefix("sensors") {

      //exception handler
      handleExceptions(simpleExceptionHandler) {

        //get
        get {
          complete(sensorService.get())
        } ~
          post {
            entity(as[SensorInput]){
              result => complete(sensorService.insert(result))
            } ~
            complete(BadRequest -> "the entity is not corresponding (not a sensor input entity)")
          } ~
          complete(Unauthorized -> "You are not authorized to access attribute levels!")
      }
    }

  val simpleExceptionHandler = ExceptionHandler {
    case ex: Exception =>
      extractUri {
        uri => {
          //logging
          logger.error("An error ha occurred when manipulating the attribute levels for the URI: " + uri, ex)

          //response
          complete(HttpResponse(
            StatusCodes.InternalServerError,
            entity = ex.asInstanceOf[Exception].getMessage()
          ))
        }
      }
  }
}
