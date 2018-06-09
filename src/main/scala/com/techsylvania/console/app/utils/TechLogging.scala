package com.techsylvania.console.app.utils

import org.slf4j.{Logger, LoggerFactory}

trait TechLogging {
  lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)
}
