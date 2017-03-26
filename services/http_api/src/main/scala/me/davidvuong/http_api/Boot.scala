package me.davidvuong.http_api

import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.syntax._
import org.log4s._

import scalaz.concurrent._
import me.davidvuong.http_api.config.Config
import me.davidvuong.http_api.http.services.MessageHttpService
import me.davidvuong.http_api.services.MessageService
import org.http4s.HttpService

object Boot extends ServerApp {
  val config: Config = Config.loadUnsafe
  val logger: Logger = getLogger

  /* http services */
  val messageService = MessageService

  /* http api */
  val httpService: HttpService = List(
    MessageHttpService(messageService).service
  ).reduce(_ orElse _)

  def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(config.http.port, config.http.host)
      .mountService(httpService)
      .start
  }
}
