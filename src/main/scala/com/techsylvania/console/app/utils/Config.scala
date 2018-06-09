package com.techsylvania.console.app.utils


import pureconfig.loadConfig

case class Config(http: HttpConfig, database: DatabaseConfig)

object Config {
  def load() =
    loadConfig[Config] match {
      case Right(config) => config
      case Left(error) =>
        throw new RuntimeException("Cannot read config file, errors:\n" + error.toList.mkString("\n"))
    }
}

private[utils] case class HttpConfig(host: String, port: Int)
private[utils] case class DatabaseConfig(databaseUrl: String, databaseName: String, username: String, password: String)