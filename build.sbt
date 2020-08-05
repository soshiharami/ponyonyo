name := "ponyonyo"

version := "0.2"

scalaVersion := "2.13.2"

resolvers += Resolver.JCenterRepository
libraryDependencies += "net.katsstuff" %% "ackcord" % "0.16.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
libraryDependencies += "net.katsstuff" %% "ackcord-requests" % "0.16.1"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0"
