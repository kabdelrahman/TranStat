package com.github.kabdelrahman.transtat.bootstrap

import java.util.concurrent.TimeUnit

import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration.Duration

trait AppConfig {
  val config: Config = ConfigFactory.load()
  val httpHost: String = config.getString("http.host")
  val httpPort: Int = config.getInt("http.port")
  val version: String = config.getString("version")

  val ttl: Duration = duration("cache.ttl")

  private def duration(part: String) =
    Duration.create(config.getDuration(part).toMillis, TimeUnit.MILLISECONDS)
}
