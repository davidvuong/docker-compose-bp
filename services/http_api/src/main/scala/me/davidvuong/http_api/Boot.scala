package me.davidvuong.http_api

import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import me.davidvuong.http_api.config.Config

import scalaz.concurrent._

object Boot extends ServerApp {

  val config = Config.loadUnsafe

  def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(config.http.port, config.http.host)
      .start
  }
}
