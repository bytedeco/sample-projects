import sbt._
import Keys._

object TesseractOcrBuild extends Build {
  def scalaSettings = Seq(
    scalaVersion := "2.11.6",
    scalacOptions ++= Seq(
      "-optimize",
      "-unchecked",
      "-deprecation")
  )

}
