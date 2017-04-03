package me.davidvuong.http_api.http.services

import scalaz._
import org.http4s.HttpService
import org.http4s.dsl._

import com.imageintelligence.http4c.ArgonautInstances._
import com.imageintelligence.http4c.ApiResponse

import me.davidvuong.http_api.services.MessageService
import me.davidvuong.http_api.domain.http.{CreateMessageRequestDto, CreateMessageResponseDto}

case class MessageHttpService(messageService: MessageService) {
  val service = HttpService {
    case req @ POST -> Root / "message" => req.decode[CreateMessageRequestDto] { m =>
      messageService.createMessage(m.message, m.clientId, m.webhookUrl).flatMap {
        case -\/(error)   => BadRequest(ApiResponse.failure(error.toString))
        case \/-(message) =>
          Ok(ApiResponse.success(CreateMessageResponseDto(message.id, message.status, message.createdAt)))
      }
    }
  }
}
