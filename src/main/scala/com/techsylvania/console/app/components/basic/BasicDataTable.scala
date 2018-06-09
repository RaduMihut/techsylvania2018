package com.techsylvania.console.app.components.basic

import com.techsylvania.console.app.components.BasicData
import com.techsylvania.console.app.utils.db.DatabaseConnector

private[basic] trait BasicDataTable {
  protected val databaseConnector: DatabaseConnector
  import databaseConnector.profile.api._

  class BasicDataSchema(tag: Tag) extends Table[BasicData](tag, "basic") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def timestamp = column[String]("timestamp")
    def air = column[Int]("air")


    def * = (id, timestamp, air) <> ((BasicData.apply _).tupled, BasicData.unapply)
  }

  protected val basics = TableQuery[BasicDataSchema]
}
