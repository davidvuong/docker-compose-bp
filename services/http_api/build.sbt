import sbt._

lazy val buildSettings = Seq(
  name                := "http_api",
  organization        := "me.davidvuong",
  version             := "0.0.1",
  scalaVersion        := "2.11.8",
  resolvers           := Depend.depResolvers,
  libraryDependencies := Depend.dependencies
)

lazy val root = (project in file(".")).settings(buildSettings: _*)
