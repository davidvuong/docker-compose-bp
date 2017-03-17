import sbt._

lazy val projectName = "http_api"

lazy val commonSettings = Seq(
  organization         := "me.davidvuong",
  version              := "0.0.1",
  scalaVersion         := "2.11.8"
)

lazy val buildSettings = Seq(
  name                 := projectName,
  mainClass in Compile := Some("me.davidvuong.http_api.Boot"),
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

lazy val root = Project(projectName, file("."))
  .settings(commonSettings: _*)
  .settings(buildSettings: _*)
