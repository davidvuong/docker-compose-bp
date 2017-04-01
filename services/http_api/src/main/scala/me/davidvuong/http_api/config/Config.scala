package me.davidvuong.http_api.config

import pureconfig.loadConfig
import scala.util.Try

case class HttpConfig(host: String, port: Int, threadCount: Int)

case class DatabaseConfig(
  driver: String,
  url: String,
  username: String,
  password: String
)
case class SqsConfig(
  url: String,
  region: String,
  accessKey: String,
  secretKey: String
)
case class Config(http: HttpConfig, database: DatabaseConfig, sqs: SqsConfig)

object Config {
  def load: Try[Config] =
    loadConfig[Config]

  def loadUnsafe: Config =
    load.get
}
