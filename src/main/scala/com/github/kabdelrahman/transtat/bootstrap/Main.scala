package com.github.kabdelrahman.transtat.bootstrap

import com.github.kabdelrahman.transtat.api.{Api, CoreActorSystem}
import com.github.kabdelrahman.transtat.model.Transaction
import com.github.kabdelrahman.transtat.service.{Cache, TransactionInMemoryCache}
import com.typesafe.scalalogging.LazyLogging
import io.github.lhotari.akka.http.health.HealthEndpoint

object Main
  extends App
    with LazyLogging
    with HealthEndpoint
    with AppConfig
    with CoreActorSystem
    with Api
    with Host {
   implicit val cache: Cache[Transaction] = new TransactionInMemoryCache
}

