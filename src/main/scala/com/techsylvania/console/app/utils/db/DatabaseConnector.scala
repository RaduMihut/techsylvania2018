package com.techsylvania.console.app.utils.db

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

class DatabaseConnector(val jdbcUrl: String, val dbUser: String, val dbPassword: String) {

  private val hikariDataSource = {
    val hikariConfig = new HikariConfig()
    hikariConfig.setJdbcUrl(jdbcUrl)
    hikariConfig.setUsername(dbUser)
    hikariConfig.setPassword(dbPassword)

    new HikariDataSource(hikariConfig)
  }

  val profile = slick.jdbc.PostgresProfile
  import profile.api._

  val db = Database.forDataSource(hikariDataSource, None)
  db.createSession()

}