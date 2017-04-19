package me.davidvuong.http_api.domain

import java.net.URL
import java.time.Instant
import java.util.UUID

case class Message(
  id: UUID,
  correlationId: UUID,
  webhookUrl: URL,
  content: String,
  status: MessageStatus,
  createdAt: Instant
)

object Message {
  def initializeMessage(content: String, correlationId: UUID, webhookUrl: URL): Message =
    Message(
      id            = UUID.randomUUID(),
      correlationId = correlationId,
      webhookUrl    = webhookUrl,
      content       = content,
      status        = MessageStatus.InProgress,
      createdAt     = Instant.now
    )
}
