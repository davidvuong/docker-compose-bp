import sbt._

object Depend {
  lazy val scalazVersion = "7.1.7"
  lazy val http4sVersion = "0.14.10"
  lazy val doobieVersion = "0.4.0"
  lazy val log4jVersion  = "2.7"

  lazy val scalaz = Seq(
    "org.scalaz" %% "scalaz-core"       % scalazVersion,
    "org.scalaz" %% "scalaz-concurrent" % scalazVersion
  )

  lazy val pureconfig = Seq(
    "com.github.melrief" %% "pureconfig" % "0.3.3"
  )

  lazy val argonaut = Seq(
    "io.argonaut" %% "argonaut" % "6.1"
  )

  lazy val http4s = Seq(
    "org.http4s" %% "http4s-dsl"          % http4sVersion,
    "org.http4s" %% "http4s-argonaut"     % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion
  )

  lazy val http4c = Seq(
    "com.imageintelligence" %% "http4c" % "0.2.5"
  )

  lazy val doobie = Seq(
    "org.tpolecat" %% "doobie-core"     % doobieVersion,
    "org.tpolecat" %% "doobie-postgres" % doobieVersion,
    "org.tpolecat" %% "doobie-hikari"   % doobieVersion,
    "org.tpolecat" %% "doobie-specs2"   % doobieVersion
  )

  lazy val sqs = Seq(
    "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.111"
  )

  lazy val logging = Seq(
    "org.log4s"                %% "log4s"           % "1.3.0",
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,
    "org.apache.logging.log4j" % "log4j-core"       % log4jVersion,
    "org.apache.logging.log4j" % "log4j-api"        % log4jVersion
  )

  lazy val scalaTestCheck = Seq(
    "org.scalatest"  %% "scalatest"  % "2.2.4"  % "test",
    "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
  )

  lazy val dependencies: Seq[ModuleID] =
    scalaz     ++
    pureconfig ++
    argonaut   ++
    http4s     ++
    http4c     ++
    doobie     ++
    sqs        ++
    logging    ++
    scalaTestCheck

  lazy val depResolvers = Seq(
    "Scalaz Bintray Repo"             at "http://dl.bintray.com/scalaz/releases",
    "ImageIntelligence Bintray Repo"  at "http://dl.bintray.com/imageintelligence/maven",
    "tpolecat"                        at "http://dl.bintray.com/tpolecat/maven",
    Resolver.sonatypeRepo("releases")
  )
}
