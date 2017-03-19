package me.davidvuong.http_api.domain

import java.net.URL
import java.util.UUID

import argonaut._
import Argonaut._

import scalaz._

case class SendMessageRequestDto(
  message: String,
  clientId: UUID,
  webhookUrl: URL
)

object SendMessageRequestDto {
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

  implicit def SendMessageRequestDtoEncodeJson: EncodeJson[SendMessageRequestDto] =
    jencode3L((m: SendMessageRequestDto) => (m.message, m.clientId, m.webhookUrl))("message", "clientId", "webhookUrl")

  implicit def SendMessageRequestDtoDecodeJson: DecodeJson[SendMessageRequestDto] =
    jdecode3L(SendMessageRequestDto.apply)("message", "clientId", "webhookUrl")
}
