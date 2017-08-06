
name := "transtat"

version := "1.0"

scalaVersion := "2.12.3"

val akkaVersion = "2.4.19"
val akkaHttpVersion = "10.0.9"
val scalaLoggingVersion = "3.7.2"
val scalaTestVersion = "3.0.1"
val log4j2Version = "2.8.2"
val mockitoV = "1.10.19"
val scalaMetricsVersion = "3.5.9"

assemblyOutputPath in assembly := file(s"target/${name.value}-$version-assembly.jar")

// Akka Actors
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaVersion

// Akka Http
libraryDependencies += "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion

// Json
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion

// API
//libraryDependencies += "ch.megard" %% "akka-http-cors" % "0.2.1"

// Testing
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
libraryDependencies += "org.mockito" % "mockito-all" % mockitoV % "test"

// Logging
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % akkaVersion

// Metrics
libraryDependencies += "nl.grons" %% "metrics-scala" % scalaMetricsVersion

// Health checks for JVM
resolvers += Resolver.jcenterRepo
libraryDependencies += "io.github.lhotari" %% "akka-http-health" % "1.0.8"

// Swagger Documentation
libraryDependencies += "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.10.0"

