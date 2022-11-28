import sbt._

object Version {
  val PlaySlick     = "5.0.0"
  val FlywayPlay    = "7.20.0"
  val H2            = "2.1.210"
  val SwaggerWebjar = "4.14.0"
  val ScalaTest     = "5.1.0"
  val ScalaMock     = "5.2.0"
}

object Library {
  val PlaySlick     = "com.typesafe.play"      %% "play-slick"         % Version.PlaySlick
  val FlywayPlay    = "org.flywaydb"           %% "flyway-play"        % Version.FlywayPlay
  val H2            = "com.h2database"          % "h2"                 % Version.H2
  val SwaggerWebjar = "org.webjars"             % "swagger-ui"         % Version.SwaggerWebjar
  val ScalaTest     = "org.scalatestplus.play" %% "scalatestplus-play" % Version.ScalaTest % Test
  val ScalaMock     = "org.scalamock"          %% "scalamock"          % Version.ScalaMock % Test
}

object Dependencies {

  val play: Seq[ModuleID] = Seq(
    Library.PlaySlick,
    Library.FlywayPlay
  )

  val database: Seq[ModuleID] = Seq(Library.H2)

  val swagger: Seq[ModuleID] = Seq(Library.SwaggerWebjar)

  val test: Seq[ModuleID] = Seq(
    Library.ScalaTest,
    Library.ScalaMock
  )
}
