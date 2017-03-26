package me.davidvuong.http_api.domain

import java.net.URL
import java.util.UUID

import argonaut._
import Argonaut._

import scalaz._

case class CreateMessageRequestDto(
  message: String,
  clientId: UUID,
  webhookUrl: URL
)

object CreateMessageRequestDto{
  def eitherDecoder[A, B](history: CursorHistory, f: A => String \/ B): A => DecodeResult[B] = {
    f(_).fold(x => DecodeResult.fail[B](x, history), x => DecodeResult.ok(x))
  }

  implicit def URLCodecJson: CodecJson[URL] = CodecJson(
    i => i.toString.asJson,
    i => i.as[String].flatMap(eitherDecoder(
      i.history,
      a => \/.fromTryCatchNonFatal(new URL(a)).leftMap(_ => "Could not decode URL")
    ))
  )

  implicit def UUIDCodecJson: CodecJson[UUID] = CodecJson(
    i => i.toString.asJson,
    i => i.as[String].flatMap(eitherDecoder(
      i.history,
      a => \/.fromTryCatchNonFatal(UUID.fromString(a)).leftMap(_ => "Could not decode UUID")
    ))
  )

  implicit def SendMessageRequestDtoCodecJson: CodecJson[CreateMessageRequestDto] =
    casecodec3(CreateMessageRequestDto.apply, CreateMessageRequestDto.unapply)("message", "clientId", "webhookUrl")
}
