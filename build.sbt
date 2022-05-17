name := "play-scala-todo"
organization := "com.todo"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.15"

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-slick" %  "5.0.0"
libraryDependencies += "com.h2database" % "h2" % "2.1.212"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test