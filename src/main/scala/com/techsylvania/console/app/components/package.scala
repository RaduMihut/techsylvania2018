package com.techsylvania.console.app

import akka.http.scaladsl.model.DateTime

package object components {
  case class BasicData(id: Int, timestamp: String, air: Int)
  case class Basic(id: Int, timestamp: String, air: Int)

  object Basic{
    def apply(bData: BasicData): Basic = Basic(bData.id, bData.timestamp, bData.air)
  }


  case class SensorData(id: Int, sensorId: String, readingTime: String, sensorType: String, measurement:Double)
  case class Sensor(id: Int, sensorId: String, readingTime: String, sensorType: String, measurement:Double)
  case class SensorInput(Date: String, IP: String, TemperatureC: Option[Double], Humidity: Option[Double], UVIntensity: Option[Double], Dustmgm3:Option[Double])

  object Sensor{
    def apply(sData: SensorData): Sensor = Sensor(sData.id, sData.sensorId, sData.readingTime, sData.sensorType, sData.measurement)
  }

}
