package me.davidvuong.http_api.repository

import doobie.free.connection.ConnectionIO
import doobie.imports.Transactor

import scalaz._
import scalaz.concurrent.Task

case class Repository(transactor: Transactor[Task]) {
  def executeOp[A](op: ConnectionIO[A]): Task[Throwable \/ A] = {
    transactor.trans(op).attempt
  }
}
