name := "play-scala-todo"
organization := "com.todo"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.15"

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-slick" %  "5.0.0"
libraryDependencies += "org.flywaydb" %% "flyway-play" % "7.20.0"
libraryDependencies += "com.h2database" % "h2" % "2.1.210"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "5.2.0" % Test