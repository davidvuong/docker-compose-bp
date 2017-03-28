package me.davidvuong.http_api.http.services

import org.http4s.HttpService
import org.http4s.dsl._
import com.imageintelligence.http4c.ArgonautInstances._
import me.davidvuong.http_api.services.MessageService
import me.davidvuong.http_api.domain.CreateMessageRequestDto

import scalaz._

case class MessageHttpService(messageService: MessageService) {
  val service = HttpService {
    case req @ POST -> Root / "message" => req.decode[CreateMessageRequestDto] { m =>
      messageService.createMessage(m.message, m.clientId, m.webhookUrl).flatMap {
        case -\/(error) => BadRequest(error.toString)
        case _          => Ok()
      }
    }
  }
}
