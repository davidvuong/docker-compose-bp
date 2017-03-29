package me.davidvuong.http_api.repository

import doobie.imports._
import doobie.postgres.imports._
import me.davidvuong.http_api.helpers.DoobieHelpers._
import me.davidvuong.http_api.domain.Message

import scalaz.\/
import scalaz.concurrent.Task

object CreateMessageRepository {
  def create(m: Message, repo: Repository): Task[\/[Throwable, Message]] = {
    val query: Update0 = sql"""
      INSERT INTO messages (id, client_id, webhook_url, content, status, created_at)
      VALUES (${m.id}, ${m.clientId}, ${m.webhookUrl}, ${m.content}, ${m.status}, ${m.createdAt})
    """.asInstanceOf[Fragment].update

    val dbOp = query.run.map(_ => m)
    repo.executeOp(dbOp)
  }
}
