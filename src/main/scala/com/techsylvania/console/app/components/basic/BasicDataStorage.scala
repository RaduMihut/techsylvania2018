package com.techsylvania.console.app.components.basic

import com.techsylvania.console.app.components.{Basic, BasicData}
import com.techsylvania.console.app.utils.db.DatabaseConnector

import scala.concurrent.{ExecutionContext, Future}

sealed trait BasicDataStorage{
  def get(): Future[Seq[BasicData]]
  def insert(basicData: BasicData): Future[BasicData]
}

class JdbcBasicDataStorage(
  val databaseConnector: DatabaseConnector
)(implicit executionContext: ExecutionContext) extends BasicDataTable with BasicDataStorage  {

  import databaseConnector._
  import databaseConnector.profile.api._

  override def get(): Future[Seq[BasicData]] = {
    for {
      data <- db.run(basics.result)
    } yield data
  }

  //insert
  override def insert(basicData: BasicData): Future[BasicData] = {
    val insertQuery = basics returning basics.map(_.id) into ((task, id) => task.copy(id = id))
    db.run(insertQuery += basicData.copy(id = 0))
  }
}
