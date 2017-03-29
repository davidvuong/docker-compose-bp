package me.davidvuong.http_api.domain

import java.time.Instant
import java.util.UUID

import argonaut._
import Argonaut._

import me.davidvuong.http_api.helpers.ArgonautHelpers._

case class CreateMessageResponseDto(
  id: UUID,
  status: MessageStatus,
  createdAt: Instant
)

object CreateMessageResponseDto {
  implicit def CreateMessageResponseDtoCodecJson: CodecJson[CreateMessageResponseDto] =
    casecodec3(CreateMessageResponseDto.apply, CreateMessageResponseDto.unapply)("id", "status", "createdAt")
}
