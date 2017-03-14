import sbt._

lazy val buildSettings = Seq(
  name                 := "http_api",
  organization         := "me.davidvuong",
  mainClass in Compile := Some("me.davidvuong.http_api.Boot"),
  version              := "0.0.1",
  scalaVersion         := "2.11.8",
  scalacOptions        := Seq(
    "-unchecked",
    "-feature",
    "-deprecation",
    "-encoding",
    "utf8",
    "-language:postfixOps",
    "-Xfatal-warnings"
  ),
  resolvers            := Depend.depResolvers,
  libraryDependencies  := Depend.dependencies
)

lazy val root = (project in file(".")).settings(buildSettings: _*)
