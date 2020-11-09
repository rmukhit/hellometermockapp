name := "hellometer-backend"
 
version := "1.0" 
      
lazy val `hellometer-backend` = (project in file(".")).enablePlugins(PlayJava)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  guice,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-core" % "5.4.9.Final",
  "org.postgresql" % "postgresql" % "9.4.1212"
)

// Let persistence.xml stay inside the generated jar in production deployment
// This is a requirement by the JPA specification
PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"