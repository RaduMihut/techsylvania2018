package com.techsylvania.console.app.components.sensors

import com.techsylvania.console.app.components.{BasicData, SensorData}
import com.techsylvania.console.app.utils.db.DatabaseConnector

import scala.concurrent.{ExecutionContext, Future}

sealed trait SensorDataStorage {
  def get(): Future[Seq[SensorData]]
  def insert(sensorData: SensorData): Future[SensorData]
}

class JdbcSensorDataStorage(
  val databaseConnector: DatabaseConnector
                           )(implicit executionContext: ExecutionContext) extends SensorDataTable with SensorDataStorage {
  import databaseConnector._
  import databaseConnector.profile.api._

  override def get(): Future[Seq[SensorData]] =
    for{
      data <- db.run(sensors.result)
      } yield data

  //insert
  override def insert(sensorData: SensorData): Future[SensorData] = {
    val insertQuery = sensors returning sensors.map(_.id) into ((task, id) => task.copy(id = id))
    db.run(insertQuery += sensorData.copy(id = 0))
  }
}
