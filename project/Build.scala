import xerial.sbt.Sonatype.SonatypeKeys._
import sbt._
import sbt.Keys._

object ScalatoolboxBuild extends Build {

  lazy val scalatoolbox = Project(
    id = "scala-toolbox",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "scala-toolbox",
      sonatypeProfileName := "org.sisioh",
      organization := "org.sisioh",
      scalaVersion := "2.11.6",
      crossScalaVersions := Seq("2.11.6", "2.10.5"),
      resolvers ++= Seq(
        "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
      ),
      libraryDependencies ++= Seq(
        "org.specs2" %% "specs2-core" % "3.6" % "test",
        "org.mockito" % "mockito-all" % "1.9.0" % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.7" % "test",
        "org.slf4j" % "slf4j-api" % "1.7.1",
        "org.clapper" %% "grizzled-slf4j" % "1.0.2"
      ),
      scalacOptions := Seq("-feature",  "-unchecked",  "-deprecation"),
      publishMavenStyle := true,
      publishArtifact in Test := false,
      pomIncludeRepository := {
        _ => false
      },
      pomExtra := (
        <url>https://github.com/sisioh/sisioh-toolbox</url>
          <licenses>
            <license>
              <name>Apache License Version 2.0</name>
              <url>http://www.apache.org/licenses/</url>
              <distribution>repo</distribution>
            </license>
          </licenses>
          <scm>
            <url>git@github.com:sisioh/sisioh-toolbox.git</url>
            <connection>scm:git:git@github.com:sisioh/sisioh-toolbox.git</connection>
          </scm>
          <developers>
            <developer>
              <id>j5ik2o</id>
              <name>Junichi Kato</name>
              <url>http://j5ik2o.me</url>
            </developer>
          </developers>
        )
    )
  )

}
