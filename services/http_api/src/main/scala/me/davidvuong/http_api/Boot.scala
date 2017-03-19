package me.davidvuong.http_api

import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import org.log4s._
import scalaz.concurrent._

import me.davidvuong.http_api.config.Config

object Boot extends ServerApp {

  val config = Config.loadUnsafe
  val logger = getLogger

  def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(config.http.port, config.http.host)
      .start
  }
}
