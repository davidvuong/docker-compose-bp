package me.davidvuong.http_api.domain.http

import java.net.URL
import java.util.UUID

import argonaut.Argonaut._
import argonaut._
import me.davidvuong.http_api.helpers.ArgonautHelpers._

case class CreateMessageRequestDto(
  message: String,
  correlationId: UUID,
  webhookUrl: URL
)

object CreateMessageRequestDto {
  implicit def SendMessageRequestDtoCodecJson: CodecJson[CreateMessageRequestDto] =
    casecodec3(CreateMessageRequestDto.apply, CreateMessageRequestDto.unapply)("message", "correlationId", "webhookUrl")
}
