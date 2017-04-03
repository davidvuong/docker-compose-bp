package me.davidvuong.http_api

import doobie.hikari.hikaritransactor
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.syntax._
import org.http4s.HttpService
import org.log4s._
import doobie.hikari.imports._

import scalaz.concurrent._
import me.davidvuong.http_api.config.Config
import me.davidvuong.http_api.http.services.MessageHttpService
import me.davidvuong.http_api.repository.Repository
import me.davidvuong.http_api.services.MessageService
import me.davidvuong.http_api.utils.SqsQueueService

object Boot extends ServerApp {
  /* general utility */
  val logger: Logger = getLogger
  val config: Config = Config.loadUnsafe

  /* database utility */
  val transactor: hikaritransactor.HikariTransactor[Task] =
    HikariTransactor[Task](
      config.database.driver,
      config.database.url,
      config.database.username,
      config.database.password
    ).unsafePerformSync

  /* sqs queue service */
  val sqsQueueService = SqsQueueService(config.sqs)

  /* database service */
  val repository: Repository = Repository(transactor)

  /* http services */
  val messageService = MessageService(repository, sqsQueueService)

  /* http api */
  val httpService: HttpService = List(
    MessageHttpService(messageService).service
  ).reduce(_ orElse _)

  def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(config.http.port, config.http.host)
      .mountService(httpService)
      .withConnectorPoolSize(config.http.threadCount)
      .start
  }
}
