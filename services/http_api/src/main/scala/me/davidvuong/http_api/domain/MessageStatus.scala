package me.davidvuong.http_api.domain

sealed trait MessageStatus

object MessageStatus {
  case class InProgress(code: String = "IN_PROGRESS") extends MessageStatus
  case class Complete(code: String = "COMPLETE") extends MessageStatus
  case class Error(code: String = "Error") extends MessageStatus
}
