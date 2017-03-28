package me.davidvuong.http_api.domain

import scalaz._

sealed trait MessageStatus

object MessageStatus {
  case object InProgress extends MessageStatus
  case object Complete extends MessageStatus
  case object Error extends MessageStatus

  def toString(status: MessageStatus): String = status match {
    case InProgress => "IN_PROGRESS"
    case Complete   => "COMPLETE"
    case Error      => "ERROR"
  }

  def fromString(code: String): String \/ MessageStatus =
    code match {
      case "IN_PROGRESS" => \/-(InProgress)
      case "COMPLETE"    => \/-(Complete)
      case "ERROR"       => \/-(Error)
      case _             => -\/(s"Unknown message status: $code")
    }
}
