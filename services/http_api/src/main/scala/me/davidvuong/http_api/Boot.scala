package me.davidvuong.http_api

import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.syntax._
import org.log4s._

import scalaz.concurrent._
import me.davidvuong.http_api.config.Config
import me.davidvuong.http_api.http.services.SendMessageHttpService
import me.davidvuong.http_api.services.SendMessageService

object Boot extends ServerApp {

  val config = Config.loadUnsafe
  val logger = getLogger

  /* http services */
  val sendMessageService = SendMessageService()

  /* http api */
  val httpService = List(
    SendMessageHttpService(sendMessageService).service
  ).reduce(_ orElse _)

  def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(config.http.port, config.http.host)
      .mountService(httpService)
      .start
  }
}
