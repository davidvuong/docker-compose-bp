package me.davidvuong.http_api.domain

sealed trait MessageStatus

object MessageStatus {
  case class InProgress(code: String = "IN_PROGRESS") extends MessageStatus
  case class Complete(code: String = "COMPLETE") extends MessageStatus
  case class Error(code: String = "ERROR") extends MessageStatus

  def fromString(code: String): MessageStatus =
    code match {
      case "IN_PROGRESS" => InProgress()
      case "COMPLETE"    => Complete()
      case "ERROR"       => Error()
    }
}
