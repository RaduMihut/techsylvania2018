package com.techsylvania.console.app.components.sensors

import akka.http.scaladsl.model.DateTime
import com.techsylvania.console.app.components.{Sensor, SensorData, SensorInput}

import scala.concurrent.{ExecutionContext, Future}

class SensorService(sensorDataStorage: SensorDataStorage)
                   (implicit executionContext: ExecutionContext) {


  def get(): Future[Seq[Sensor]] =
    for {
      data <- sensorDataStorage.get()
      businessData = data.map(Sensor(_))
    } yield businessData

  def insert(sensor: SensorInput): Unit = {
    val timestamp = DateTime.now.toString + "+00"

    //temp
    sensor.TemperatureC match {
      case Some(value) => for {
        res <- sensorDataStorage.insert(SensorData(id = 0, sensorId = sensor.IP, readingTime = timestamp, sensorType = "temp", measurement = value))
        data = Sensor(res)
      } yield data
      case None => Unit
    }

    //dust
    sensor.Dustmgm3 match {
      case Some(value) => for {
        res <- sensorDataStorage.insert(SensorData(id = 0, sensorId = sensor.IP, readingTime = timestamp, sensorType = "dust", measurement = value))
        data = Sensor(res)
      } yield data

      case None => Unit
    }

    //humidity
    sensor.Humidity match {
      case Some(value) => for {
        res <- sensorDataStorage.insert(SensorData(id = 0, sensorId = sensor.IP, readingTime = timestamp, sensorType = "humidity", measurement = value))
        data = Sensor(res)
      } yield data

      case None => Unit
    }

    //uv
    sensor.UVIntensity match {
      case Some(value) => for {
        res <- sensorDataStorage.insert(SensorData(id = 0, sensorId = sensor.IP, readingTime = timestamp, sensorType = "uv", measurement = value))
        data = Sensor(res)
      } yield data

      case None => Unit
    }
  }
}
