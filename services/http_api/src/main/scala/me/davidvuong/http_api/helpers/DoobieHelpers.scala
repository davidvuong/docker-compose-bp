package me.davidvuong.http_api.helpers

import java.net.{URL, URLDecoder}
import java.time.Instant

import doobie.imports._
import me.davidvuong.http_api.domain.MessageStatus

object DoobieHelpers {
  implicit val URLMeta: Meta[URL] =
    Meta[String].nxmap(
      i => new URL(i),
      i => URLDecoder.decode(i.toString, "UTF-8")
    )

  implicit val InstantMeta: Meta[Instant] =
    Meta[java.sql.Timestamp].nxmap(
      i => i.toInstant,
      i => new java.sql.Timestamp(i.toEpochMilli)
    )

  implicit val MessageStatusMeta2: Meta[MessageStatus] =
    Meta[String].nxmap(
      i => MessageStatus.fromString(i).fold(sys.error, identity),
      i => MessageStatus.toString(i)
    )
}
