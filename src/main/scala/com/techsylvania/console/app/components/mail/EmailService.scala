package com.techsylvania.console.app.components.mail

import scala.concurrent.Future


trait EmailService {
  def sendMail(to: String, messageTitle: String, message: String): Future[Unit]
  def isMailValid(email: String): Boolean
}
