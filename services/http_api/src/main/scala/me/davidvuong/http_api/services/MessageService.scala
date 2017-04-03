package me.davidvuong.http_api.services

import java.net.URL
import java.util.UUID

import scalaz._
import scalaz.concurrent.Task

import argonaut._
import Argonaut._

import me.davidvuong.http_api.domain.Message
import me.davidvuong.http_api.domain.queues.TransformMessageEgress
import me.davidvuong.http_api.repository.{CreateMessageRepository, Repository}
import me.davidvuong.http_api.utils.SqsQueueService

case class MessageService(repo: Repository, queue: SqsQueueService) {
  def createMessage(content: String, clientId: UUID, webhookUrl: URL): Task[\/[Throwable, Message]] = {
    val stagedMessage = Message.initializeMessage(content, clientId, webhookUrl)

    CreateMessageRepository.create(stagedMessage, repo).flatMap {
      case -\/(error)   => Task.now(-\/(error))
      case \/-(message) =>
        val egressMessage = TransformMessageEgress.fromMessage(message)
        queue.send(egressMessage.asJson.nospaces).unsafePerformSync
        Task.now(\/-(message))
    }
  }
}
