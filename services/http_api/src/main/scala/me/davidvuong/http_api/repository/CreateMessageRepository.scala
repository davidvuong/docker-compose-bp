package me.davidvuong.http_api.repository

import doobie.imports._
import doobie.postgres.imports._

import me.davidvuong.http_api.helpers.DoobieHelpers._
import me.davidvuong.http_api.domain.Message

object CreateMessageRepository {
  def create(m: Message): ConnectionIO[Message] = {
    val query = sql"""
      INSERT INTO messages (id, client_id, webhook_url, content, status, created_at)
      VALUES (${m.id}, ${m.clientId}, ${m.webhookUrl}, ${m.content}, ${m.status}, ${m.createdAt})
    """.update
    query.run.map(_ => m)
  }
}
