package me.davidvuong.http_api.repository

import doobie.imports._
import doobie.postgres.imports._
import me.davidvuong.http_api.helpers.DoobieHelpers._
import me.davidvuong.http_api.domain.Message

object CreateMessageRepository {
  def create(m: Message): ConnectionIO[Message] = {
    val query: Update0 = sql"""
      INSERT INTO messages (id, correlation_id, webhook_url, content, status, created_at)
      VALUES (${m.id}, ${m.correlationId}, ${m.webhookUrl}, ${m.content}, ${m.status}, ${m.createdAt})
    """.asInstanceOf[Fragment].update
    query.run.map(_ => m)
  }
}
