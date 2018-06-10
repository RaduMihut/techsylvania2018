package com.techsylvania.console.app.components.sensors

import akka.http.scaladsl.model.DateTime
import com.techsylvania.console.app.components.{SensorData}
import com.techsylvania.console.app.utils.db.DatabaseConnector

private[sensors] trait SensorDataTable {

  protected val databaseConnector: DatabaseConnector
  import databaseConnector.profile.api._

  class SensorDataSchema(tag: Tag) extends Table[SensorData](tag, "sensors") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def sensorId = column[String]("sensor_id")
    def readingTime = column[String]("reading_time")
    def sensorType = column[String]("sensor_type")
    def measurement = column[Double]("measurement")

    def * = (id, sensorId, readingTime, sensorType, measurement) <> ((SensorData.apply _).tupled, SensorData.unapply)
  }

  protected val sensors = TableQuery[SensorDataSchema]
}
