name := "poc-akka-http"

version := "1.0"

scalaVersion := "2.11.8"

organization := "Inocybe Technologies"

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

fork := true

libraryDependencies ++= {
  val akkaV = "2.4.16"
  val akkaHTTPv = "10.0.0"
  Seq(
    "com.typesafe.akka"                       %%  "akka-actor"             % akkaV,
    "com.typesafe.akka"                       %%  "akka-slf4j"             % akkaV,
    "com.typesafe.akka"                       %%  "akka-http-spray-json"   % akkaHTTPv,
    "com.typesafe.akka"                       %%  "akka-http"              % akkaHTTPv,
    "com.typesafe.akka"                       %%  "akka-http-core"         % akkaHTTPv,
    "com.typesafe.akka"                       %%  "akka-parsing"           % akkaHTTPv
  )
}