package com.techsylvania.console.app.components.sensors

import com.techsylvania.console.app.components.{Sensor, SensorData, SensorInput}

import scala.concurrent.{ExecutionContext, Future}

class SensorService(sensorDataStorage: SensorDataStorage)
                   (implicit executionContext: ExecutionContext) {


  def get(): Future[Seq[Sensor]] =
    for {
      data <- sensorDataStorage.get()
      businessData = data.map(Sensor(_))
    } yield businessData

  def insert(sensor: SensorInput): Future[Sensor] =
    for{
      res <- sensorDataStorage.insert(SensorData(id = 0,sensorId = "",readingTime = sensor.date, sensorType = sensor.ip, measurement1 = sensor.temperatureC,measurement2 = sensor.humidity,measurement3 = 0/*sensor.dustmgm3*/))
      data = Sensor(res)
    } yield data
}
