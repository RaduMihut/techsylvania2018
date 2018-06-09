package com.techsylvania.console.app.components.mail

import java.util.Properties

import com.techsylvania.console.app.utils.{Config, TechLogging}
import javax.mail._
import javax.mail.internet.{InternetAddress, MimeMessage}

import scala.concurrent.{ExecutionContext, Future}

class EmailServiceImpl()(implicit executionContext: ExecutionContext) extends EmailService with TechLogging {
  val config = Config.load()

  val props = new Properties
  props.put("mail.smtp.host", "smtp.gmail.com") //SMTP Host
  props.put("mail.smtp.socketFactory.port", "465") //SSL Port
  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory") //SSL Factory Class
  props.put("mail.smtp.auth", "true") //Enabling SMTP Authentication
  props.put("mail.smtp.port", "465") //SMTP Port

  val auth: Authenticator = new Authenticator() {
    //override the getPasswordAuthentication method
    override protected def getPasswordAuthentication = new PasswordAuthentication(config.email.adminEmail, config.email.adminEmailPassword)
  }

  val session = Session.getInstance(props, auth)

  override def sendMail(to: String, messageTitle: String, message: String): Future[Unit] = Future {
    val msg = new MimeMessage(session)
    //headers
    //      msg.addHeader("Content-type", "text/HTML; charset=UTF-8")
    msg.addHeader("Content-type", "HTML; charset=UTF-8")
    msg.addHeader("format", "flowed")
    msg.addHeader("Content-Transfer-Encoding", "8bit")

    //addresses
    msg.setFrom(new InternetAddress("no_reply@healthsnap.com", "Health survey"))
    msg.setRecipient(Message.RecipientType.TO, InternetAddress.parse(to, false).head)

    //message
    msg.setSubject(messageTitle, "UTF-8")
    msg.setContent(message, "text/html; charset=utf-8")
    //    msg.setText(message, "UTF-8")

    //send
    logger.info("Message is ready")
    Transport.send(msg)
    logger.info("EMail Sent Successfully!!")
    logger.info(s"Send to: ${to}")
  }

  override def isMailValid(email: String): Boolean = email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
}
