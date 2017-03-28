package me.davidvuong.http_api.services

import java.net.URL
import java.util.UUID

import me.davidvuong.http_api.domain.Message
import me.davidvuong.http_api.repository.{CreateMessageRepository, Repository}

import scalaz._
import scalaz.concurrent.Task

case class MessageService(repo: Repository) {
  def createMessage(content: String, clientId: UUID, webhookUrl: URL): Task[\/[Throwable, Message]] = {
    val message = Message.initializeMessage(content, clientId, webhookUrl)
    CreateMessageRepository.create(message, repo)
  }
}
