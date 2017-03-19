package me.davidvuong.http_api.http.services

import org.http4s.HttpService
import org.http4s.dsl._

import me.davidvuong.http_api.services.SendMessageService

case class SendMessageHttpService(httpService: SendMessageService) {
  val service = HttpService {
    case req @ POST -> Root / "send-message" =>
      Ok("OK")
  }
}
