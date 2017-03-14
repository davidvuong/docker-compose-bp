package me.davidvuong.http_api

import org.http4s.server.ServerApp
import org.http4s.server.blaze.BlazeBuilder

import me.davidvuong.http_api.config.Config

object Boot extends ServerApp {

  val config = Config.loadUnsafe

  def server(args: List[String]) = {
    BlazeBuilder
      .bindHttp(config.http.port, config.http.host)
      .start
  }
}
