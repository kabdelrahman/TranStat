package com.github.kabdelrahman.transtat.bootstrap

import com.github.kabdelrahman.transtat.api.{Api, CoreActorSystem}
import com.typesafe.scalalogging.LazyLogging
import io.github.lhotari.akka.http.health.HealthEndpoint

object Main
  extends App
    with LazyLogging
    with HealthEndpoint // TODO enable Metrics for HealthCheck
    with AppConfig
    with CoreActorSystem
    with Api
    with Host

