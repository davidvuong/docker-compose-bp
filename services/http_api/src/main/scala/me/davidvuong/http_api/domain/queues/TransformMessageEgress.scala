package me.davidvuong.http_api.domain.queues

import java.time.Instant
import java.util.UUID

import argonaut._
import Argonaut._

import me.davidvuong.http_api.helpers.ArgonautHelpers._
import me.davidvuong.http_api.domain.Message

case class TransformMessageEgress(
  id: UUID,
  content: String,
  createdAt: Instant
)

object TransformMessageEgress {
  def fromMessage(message: Message): TransformMessageEgress =
    TransformMessageEgress(message.id, message.content, message.createdAt)

  implicit def TransformMessageEgressCodecJson: CodecJson[TransformMessageEgress] =
    casecodec3(TransformMessageEgress.apply, TransformMessageEgress.unapply)("id", "content", "createdAt")
}
