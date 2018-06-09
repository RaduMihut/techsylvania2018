package com.techsylvania.console.app.components.basic

import com.techsylvania.console.app.components.Basic

import scala.concurrent.{ExecutionContext, Future}

class BasicService(
                  basicDataStorage: BasicDataStorage
                  )(implicit executionContext: ExecutionContext) {

  def get(): Future[Seq[Basic]] = {
    for{
      data <- basicDataStorage.get()
      businessData = data.map(Basic(_))
    } yield businessData
  }
}
