// Name of the project
name := "tesseract-ocr"

version := "0.1.0"

// Version of Scala used by the project
scalaVersion := "2.11.7"

val javacppVersion = "1.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-optimize", "-Xlint")

// Some dependencies like `javacpp` are packaged with maven-plugin packaging
classpathTypes += "maven-plugin"

// Platform classifier for native library dependencies
lazy val platform = org.bytedeco.javacpp.Loader.getPlatform

libraryDependencies ++= Seq(
  "org.bytedeco" % "javacv" % javacppVersion,
  "org.bytedeco.javacpp-presets" % "tesseract" % ("3.04-"+ javacppVersion) classifier "",
  "org.bytedeco.javacpp-presets" % "tesseract" % ("3.04-"+ javacppVersion) classifier platform,
  "org.bytedeco.javacpp-presets" % "opencv" % ("3.0.0-" + javacppVersion) classifier "",
  "org.bytedeco.javacpp-presets" % "opencv" % ("3.0.0-" + javacppVersion) classifier platform,
  "org.bytedeco.javacpp-presets" % "leptonica" % ("1.72-"+ javacppVersion) classifier "",
  "org.bytedeco.javacpp-presets" % "leptonica" % ("1.72-"+ javacppVersion) classifier platform
)

// Used for testing local builds and snapshots of JavaCPP/JavaCV
//resolvers ++= Seq(
//  Resolver.sonatypeRepo("snapshots"),
//  // Use local maven repo for local javacv builds
//  "Local Maven Repository" at "file:///" + Path.userHome.absolutePath + "/.m2/repository"
//)

// fork a new JVM for 'run' and 'test:run'
fork := true

// Set the prompt (for this build) to include the project id.
shellPrompt in ThisBuild := { state => "sbt:" + Project.extract(state).currentRef.project + "> " }
