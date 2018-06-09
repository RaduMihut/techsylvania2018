package com.techsylvania.console.app.http.routes

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.http.scaladsl.server.Directives._
import StatusCodes._
import com.techsylvania.console.app.components.Basic
import com.techsylvania.console.app.components.basic.BasicService
import com.techsylvania.console.app.utils.TechLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class BasicRoutes(basicService: BasicService)(implicit executionContext: ExecutionContext) extends FailFastCirceSupport with TechLogging {

//  import com.techsylvania.console.app.components.TechJsonFormats._

  val route: Route =

    pathPrefix("basic") {


        //exception handler
        handleExceptions(simpleExceptionHandler) {

          //get
          get {
            complete(Basic(1, "", 1).asJson)
          } ~
            post {
              complete("OK")
            } ~
            complete(Unauthorized -> "You are not authorized to access attribute levels!")
        }
    }

  val simpleExceptionHandler = ExceptionHandler {
    case ex: Exception =>
      extractUri {
        uri =>
        {
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
