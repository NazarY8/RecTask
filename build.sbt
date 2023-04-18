name := "ICEOTask"

version := "0.2"

scalaVersion := "2.13.10"

//required to avoid a potential import http4sBlaze error
val http4sVersion = "0.23.18"
val http4sBlaze = "0.23.13"


libraryDependencies ++= Seq(
  "co.fs2" %% "fs2-core" % "3.6.1",
  "co.fs2" %% "fs2-io" % "3.6.1",
  "com.github.fd4s" %% "fs2-kafka" % "2.6.0",
  "com.github.pureconfig" %% "pureconfig" % "0.17.1",
  "io.circe" %% "circe-generic" % "0.14.3",
  "org.typelevel" %% "cats-core" % "2.6.1",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sBlaze,
  "org.http4s" %% "http4s-blaze-client" % http4sBlaze,
  "org.scalatest" %% "scalatest" % "3.2.10" % "test"
)
