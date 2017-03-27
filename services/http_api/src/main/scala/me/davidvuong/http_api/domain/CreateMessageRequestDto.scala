package me.davidvuong.http_api.domain

import java.net.URL
import java.util.UUID

import argonaut._
import Argonaut._

import me.davidvuong.http_api.helpers.ArgonautHelpers._

case class CreateMessageRequestDto(
  message: String,
  clientId: UUID,
  webhookUrl: URL
)

object CreateMessageRequestDto{
  implicit def SendMessageRequestDtoCodecJson: CodecJson[CreateMessageRequestDto] =
    casecodec3(CreateMessageRequestDto.apply, CreateMessageRequestDto.unapply)("message", "clientId", "webhookUrl")
}
