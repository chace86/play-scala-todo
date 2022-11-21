import com.typesafe.sbt.packager.docker.DockerChmodType
import com.typesafe.sbt.packager.docker.DockerPermissionStrategy

name         := "play-scala-todo"
organization := "com.todo"

version := "1.0.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin, AshScriptPlugin)

scalaVersion := "2.12.15"

libraryDependencies += guice
libraryDependencies += "com.typesafe.play"      %% "play-slick"         % "5.0.0"
libraryDependencies += "org.flywaydb"           %% "flyway-play"        % "7.20.0"
libraryDependencies += "com.h2database"          % "h2"                 % "2.1.210"
libraryDependencies += "org.webjars"             % "swagger-ui"         % "4.14.0"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies += "org.scalamock"          %% "scalamock"          % "5.2.0" % Test

// Play Swagger
swaggerPrettyJson       := true
swaggerV3               := true
swaggerDomainNameSpaces := Seq("models")

// SBT Native Packager
dockerChmodType          := DockerChmodType.UserGroupWriteExecute
dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
dockerBaseImage          := "openjdk:8-jre-alpine"
dockerUpdateLatest       := true
