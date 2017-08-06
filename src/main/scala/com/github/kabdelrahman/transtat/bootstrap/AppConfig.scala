package com.github.kabdelrahman.transtat.bootstrap

import com.typesafe.config.{Config, ConfigFactory}

trait AppConfig {
  val config: Config = ConfigFactory.load()
  val httpHost: String = config.getString("http.host")
  val httpPort: Int = config.getInt("http.port")
  val version: String = config.getString("version")
}
