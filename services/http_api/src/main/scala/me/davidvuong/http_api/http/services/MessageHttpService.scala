package me.davidvuong.http_api.http.services

import org.http4s.HttpService
import org.http4s.dsl._

import com.imageintelligence.http4c.ArgonautInstances._

import me.davidvuong.http_api.services.MessageService
import me.davidvuong.http_api.domain.CreateMessageRequestDto

case class MessageHttpService(messageService: MessageService.type) {
  val service = HttpService {
    case req @ POST -> Root / "message" => req.decode[CreateMessageRequestDto] { message =>
      messageService.createMessage(message.message, message.clientId, message.webhookUrl)
      Ok("OK")
    }
  }
}
