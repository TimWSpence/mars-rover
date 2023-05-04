scalaVersion := "3.2.2"
name := "mars-rover"
organization := "org.github.timwspence"
version := "1.0"


lazy val root = (project in file(".")).
  settings(
    name := "mars-rover",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.9.0",
      "org.scalameta" %% "munit" % "0.7.29" % Test
    )
  )
