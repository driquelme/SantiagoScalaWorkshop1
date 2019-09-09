name := "Cats Workshop - Santiago Scala"

version := "0.1"

scalaVersion := "2.13.0"

val Http4sVersion     = "0.21.0-M3"
val CirceVersion      = "0.12.0-M4"
val ScalaTestVersion  = "3.0.8"
val LogbackVersion    = "1.2.3"
val DoobieVersion     = "0.8.0-RC1"
val H2Version         = "1.4.199"
val PureConfigVersion = "0.11.1"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    libraryDependencies ++= Seq(
      "org.http4s"            %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"            %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"            %% "http4s-circe"        % Http4sVersion,
      "org.http4s"            %% "http4s-dsl"          % Http4sVersion,
      "io.circe"              %% "circe-generic"       % CirceVersion,
      "ch.qos.logback"        % "logback-classic"      % LogbackVersion,
      "org.tpolecat"          %% "doobie-core"        % DoobieVersion,
      "com.github.pureconfig" %% "pureconfig"          % PureConfigVersion,
      "com.h2database"        % "h2"                   % H2Version,
      "org.scalatest"         %% "scalatest"           % ScalaTestVersion % "it, test",
      "org.http4s"            %% "http4s-blaze-client" % Http4sVersion % "it,test"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.0")
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings"
)
