package me.davidvuong.http_api.http.services

import argonaut.DecodeJson
import org.http4s.HttpService
import org.http4s.dsl._
import me.davidvuong.http_api.services.SendMessageService
import me.davidvuong.http_api.domain.SendMessageRequestDto

case class SendMessageHttpService(httpService: SendMessageService) {
  implicit val sendMessageRequestDtoDecodeJson: DecodeJson[SendMessageRequestDto] = SendMessageRequestDto.SendMessageRequestDtoDecodeJson

  val service = HttpService {
    case req @ POST -> Root / "message" => req.decode[SendMessageRequestDto] { message =>
      println(s"${message.clientId}, ${message.webhookUrl}, ${message.message}")
      Ok("OK")
    }
  }
}
