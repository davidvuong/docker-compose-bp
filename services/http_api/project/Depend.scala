import sbt._

object Depend {

  lazy val scalazVersion = "7.1.7"
  lazy val http4sVersion = "0.14.10"

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

  lazy val fs2sqs = Seq(
    "com.imageintelligence" %% "fs2-sqs" % "1.0.3"
  )

  lazy val logging = Seq(
    "org.log4s"                %% "log4s"           % "1.3.0",
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.7",
    "org.apache.logging.log4j" % "log4j-core"       % "2.7",
    "org.apache.logging.log4j" % "log4j-api"        % "2.7"
  )

  lazy val scalaTestCheck = Seq(
    "org.scalatest"  %% "scalatest"  % "2.2.4"  % "test",
    "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
  )

  lazy val dependencies =
    scalaz     ++
    pureconfig ++
    argonaut   ++
    http4s     ++
    http4c     ++
    fs2sqs     ++
    logging    ++
    scalaTestCheck

  lazy val depResolvers = Seq(
    "Scalaz Bintray Repo"             at "http://dl.bintray.com/scalaz/releases",
    "ImageIntelligence Bintray Repo"  at "http://dl.bintray.com/imageintelligence/maven",
    Resolver.sonatypeRepo("releases")
  )
}
