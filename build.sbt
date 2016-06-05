name := """gilt-trest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

routesImport ++= Seq(
  "com.gilt.gilt.trest.v0.Bindables._"
)

libraryDependencies ++= Seq(
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
