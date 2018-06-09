package com.techsylvania.console.app.components

import io.circe.{Decoder, DecodingFailure, Encoder, Json}

import scala.util.{Failure, Success, Try}


object TechJsonFormats {

  def enumDecoder[E <: Enumeration](enum: E) = Decoder.instance { hc ⇒
    Try(hc.as[String] map (e ⇒ enum.withName(e))) match {
      case Failure(ex) ⇒ throw DecodingFailure(ex.getMessage, hc.history)
      case Success(eVal) ⇒ eVal
    }
  }

  def enumEncoder[E <: Enumeration](enum: E) = Encoder.instance[E#Value] { e ⇒
    Json.fromString(e.toString)
  }

}