organization := "net.noresttherein"

name := "slang"

version := "moonshine"

scalaVersion := "2.11.12"

fork in Compile := true

javaOptions in Compile ++= Seq("-Xmx2G")


testOptions in Test ++= Seq(Tests.Filter(s => !s.endsWith("Props")))


libraryDependencies ++= Seq(
	"com.chuusai" %% "shapeless" % "2.3.3",
	"org.scala-lang" % "scala-library" % "2.11.12",
	"org.scala-lang" % "scala-reflect" % "2.11.12",
	"org.scalatest" %% "scalatest" % "2.2.4" % "test",
	"org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
)


scalacOptions ++= Seq(
//	"-Ylog-classpath",
	"-Xlog-implicits",
	"-Xexperimental",
	"-feature",
	"-deprecation",
	"-language:postfixOps",
	"-language:implicitConversions",
	"-language:higherKinds",
	"-language:reflectiveCalls",
	"-language:existentials"
)



