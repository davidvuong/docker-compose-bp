package me.davidvuong.http_api.config

import pureconfig.loadConfig
import scala.util.Try

case class HttpConfig(host: String, port: Int)

case class Config(http: HttpConfig)

object Config {

  def load: Try[Config] =
    loadConfig[Config]

  def loadUnsafe: Config =
    load.get
}
