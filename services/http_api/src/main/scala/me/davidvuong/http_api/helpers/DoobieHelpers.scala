package me.davidvuong.http_api.helpers

import java.net.{URL, URLDecoder}
import java.time.Instant

import doobie.imports._

import me.davidvuong.http_api.domain._

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

  /* Custom type metas */

  implicit val MessageStatusMeta: Meta[MessageStatus] =
    Meta[String].nxmap(MessageStatus.fromString, _.toString)
}
