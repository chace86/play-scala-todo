import com.typesafe.sbt.packager.docker.DockerChmodType
import com.typesafe.sbt.packager.docker.DockerPermissionStrategy

name         := "play-scala-todo"
organization := "com.todo"

version := "1.0.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin, AshScriptPlugin)

scalaVersion := "2.13.14"

libraryDependencies ++= Dependencies.play ++
  Dependencies.database ++
  Dependencies.swagger ++
  Dependencies.test :+
  guice

// Play Swagger
swaggerPrettyJson       := true
swaggerV3               := true
swaggerDomainNameSpaces := Seq("models")

// SBT Native Packager
dockerChmodType          := DockerChmodType.UserGroupWriteExecute
dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
dockerBaseImage          := "eclipse-temurin:11-jre-alpine"
dockerUpdateLatest       := true
