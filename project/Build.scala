import sbt._
import sbt.Keys._

object ScalatoolboxBuild extends Build {

  lazy val scalatoolbox = Project(
    id = "scala-toolbox",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "scala-toolbox",
      organization := "org.sisioh",
      version := "0.0.9-SNAPSHOT",
      scalaVersion := "2.11.0",
      crossScalaVersions := Seq("2.11.0", "2.10.4"),
      libraryDependencies ++= Seq(
        "org.specs2" %% "specs2" % "2.3.12" % "test",
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
      publishTo <<= version {
        (v: String) =>
          val nexus = "https://oss.sonatype.org/"
          if (v.trim.endsWith("SNAPSHOT"))
            Some("snapshots" at nexus + "content/repositories/snapshots")
          else
            Some("releases" at nexus + "service/local/staging/deploy/maven2")
      },
      pomExtra := (
        <url>https://github.com/sisioh/sisioh-dddbase</url>
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
