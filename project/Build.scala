import sbt._
import sbt.Keys._

object ScalatoolboxBuild extends Build {

  lazy val scalatoolbox = Project(
    id = "scala-toolbox",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "scala-toolbox",
      organization := "org.sisioh",
      version := "0.0.3",
      scalaVersion := "2.10.1",
      libraryDependencies ++= Seq(
        "org.specs2" %% "specs2" % "1.14" % "test",
        "org.mockito" % "mockito-all" % "1.9.0" % "test",
        "org.slf4j" % "slf4j-api" % "1.7.1",
        "ch.qos.logback" % "logback-classic" % "1.0.7" % "test",
        "org.clapper" %% "grizzled-slf4j" % "1.0.1"
      ),
      scalacOptions := Seq("-feature",  "-unchecked",  "-deprecation"),
      publish
    )
  )

  def publish = publishTo <<= (version) {
    version: String =>
      if (version.trim.endsWith("SNAPSHOT")) {
        Some(Resolver.file("snaphost", new File("./repos/snapshot")))
      } else {
        Some(Resolver.file("release", new File("./repos/release")))
      }
  }
}
