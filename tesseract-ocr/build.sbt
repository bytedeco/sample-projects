name := "tesseract-ocr"

version := "0.1.0"

//logLevel := Level.Error //work around for sbt false positive for multiple duplicate dependencies

val javacppVersion = "1.0"

// Platform classifier for native library dependencies
lazy val platform = org.bytedeco.javacpp.Loader.getPlatform

libraryDependencies ++= Seq(
  "org.bytedeco"                 % "javacpp"         % javacppVersion,
  "org.bytedeco"                 % "javacv"          % javacppVersion,
  "org.bytedeco.javacpp-presets" % "opencv" % ("3.0.0-" + javacppVersion) classifier "",
  "org.bytedeco.javacpp-presets" % "opencv" % ("3.0.0-" + javacppVersion) classifier platform,
  "org.bytedeco.javacpp-presets" % "tesseract" % ("3.03-rc1-"+ javacppVersion) classifier "",
  "org.bytedeco.javacpp-presets" % "tesseract" % ("3.03-rc1-"+ javacppVersion) classifier platform,
  "org.bytedeco.javacpp-presets" % "leptonica" % ("1.72-"+ javacppVersion) classifier "",
  "org.bytedeco.javacpp-presets" % "leptonica" % ("1.72-"+ javacppVersion) classifier platform
)

autoCompilerPlugins := true

// fork a new JVM for 'run' and 'test:run'
fork := true
