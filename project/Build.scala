import sbt._
import sbt.Keys._

object ScalatoolboxBuild extends Build {

  lazy val scalatoolbox = Project(
    id = "scala-toolbox",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "scala-toolbox",
      organization := "org.sisioh.scala.toolbox",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.1",
      libraryDependencies ++= Seq(
        "org.clapper" %% "grizzled-slf4j" % "1.0.1"
      )
      // add other settings here
    )
  )
}
