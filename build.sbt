name := """hashboard"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies += "com.websudos" % "phantom-dsl_2.10" % "1.4.0"

libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.2"

libraryDependencies += "joda-time" % "joda-time" % "2.5"

libraryDependencies += "org.scala-lang.modules" % "scala-async_2.10" % "0.9.2"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.2.2"

libraryDependencies += "com.google.code.gson" % "gson" % "2.3.1"

libraryDependencies += "com.rometools" % "rome" % "1.5.0"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.7"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.7"

libraryDependencies += "net.jpountz.lz4" % "lz4" % "1.3.0"

libraryDependencies += "org.xerial.snappy" % "snappy-java" % "1.1.1.6"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.3.6"

libraryDependencies += "org.jsoup" % "jsoup" % "1.8.1"

libraryDependencies += "com.syncthemall" % "goose" % "2.1.25"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.3.3"

libraryDependencies += "com.github.slugify" % "slugify" % "2.1.2"

libraryDependencies += "com.syncthemall" % "boilerpipe" % "1.2.2"

libraryDependencies += "com.livestream" %% "scredis" % "2.0.2"

libraryDependencies += "net.debasishg" %% "redisclient" % "2.13"

libraryDependencies += "net.codingwell" % "scala-guice_2.10" % "4.0.0-beta5"

libraryDependencies += "org.facebook4j" % "facebook4j-core" % "2.2.2"

libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.2"














