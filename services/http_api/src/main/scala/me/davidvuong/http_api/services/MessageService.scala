package me.davidvuong.http_api.services

import java.net.URL
import java.util.UUID

object MessageService {
  def createMessage(message: String, clientId: UUID, webhookUrl: URL): Unit = {
    println(message)
    println(clientId)
    println(webhookUrl)
  }
}
