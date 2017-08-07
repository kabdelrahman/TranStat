package com.github.kabdelrahman.transtat.bootstrap

import com.github.kabdelrahman.transtat.metrics.AppWideMetrics
import com.github.kabdelrahman.transtat.api.{Api, CoreActorSystem, CoreActors}
import com.typesafe.scalalogging.LazyLogging
import io.github.lhotari.akka.http.health.HealthEndpoint

object Main
  extends App
    with LazyLogging
    with HealthEndpoint
    with AppConfig
    with CoreActorSystem
    with CoreActors
    with Api
    with Host{
  implicit val metrics = AppWideMetrics
}

