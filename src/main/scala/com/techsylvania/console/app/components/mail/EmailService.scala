package com.techsylvania.console.app.components.mail

import scala.concurrent.Future

/**
 * Created by mihai on 2/6/18.
 */
trait EmailService {
  def sendMail(to: String, messageTitle: String, message: String): Future[Unit]
  def isMailValid(email: String): Boolean
}
