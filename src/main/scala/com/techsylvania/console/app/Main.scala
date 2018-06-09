package com.techsylvania.console.app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.techsylvania.console.app.components.basic.{BasicService, JdbcBasicDataStorage}
import com.techsylvania.console.app.http.HttpRoute
import com.techsylvania.console.app.utils.Config
import com.techsylvania.console.app.utils.db.{DatabaseConnector, DatabaseMigrationManager}

import scala.concurrent.ExecutionContext

object Main extends App {
  startApplication()

  def startApplication() = {
    implicit val actorSystem = ActorSystem()
    implicit val executor: ExecutionContext = actorSystem.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

//    logger.info("Starting Up HealthSnApp Backend Server.............")

    //config
    val config = Config.load()

//    logger.info(s"Config is ${config.toString}")
//    logger.info(s"Binding To: ${config.http}........................")

    //migration
    migrateDatabase(config)

    //create routes
    Http().bindAndHandle(CreateRoutes(config).route, config.http.host, config.http.port)
  }

  private[this] def CreateRoutes(config: Config)(implicit executionContext: ExecutionContext): HttpRoute = {
    //connector
    val databaseConnector = createDatabaseConnector(config)

    //language
    val basicDataStorage = new JdbcBasicDataStorage(databaseConnector)
    val basicService = new BasicService(basicDataStorage)


    //routes
    val httpRoute = new HttpRoute(
      basicService
    )

    httpRoute
  }

  private[this] def migrateDatabase(config: Config) = {
    //migration
    val migrationManager = new DatabaseMigrationManager(
      s"jdbc:postgresql://${config.database.databaseUrl}/${config.database.databaseName}",
      config.database.username,
      config.database.password
    )
    migrationManager.migrateDatabaseSchema()
  }

  private def createDatabaseConnector(config: Config): DatabaseConnector = {
    new DatabaseConnector(
      s"jdbc:postgresql://${config.database.databaseUrl}/${config.database.databaseName}",
      config.database.username,
      config.database.password
    )
  }
}