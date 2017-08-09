package com.github.kabdelrahman.transtat.api

import akka.actor.ActorRef
import akka.http.scaladsl.server.{Route, RouteConcatenation}
import com.github.kabdelrahman.transtat.bootstrap.AppConfig
import com.github.kabdelrahman.transtat.metrics.AppWideMetrics
import com.github.kabdelrahman.transtat.persistence.{CacheController, ResetTickWheel, Tick}
import com.github.kabdelrahman.transtat.service.stats.StatService
import com.github.kabdelrahman.transtat.service.transaction.TransactionService
import com.github.kabdelrahman.transtat.swagger.SwaggerDocumentationService

import scala.concurrent.duration._

trait Api extends RouteConcatenation {
  this: Core with AppConfig =>

  private implicit val _ = system.dispatcher

  implicit val metrics = AppWideMetrics

  implicit val cacheController: ActorRef = system.actorOf(CacheController())

  // TODO refactor the scheduling part to mock it in tests
  // non-cancellable scheduler for cache controlling (ticking every 1 second to refresh cache)
  system.scheduler.schedule(0.seconds, cacheTick, cacheController, Tick)
  system.scheduler.schedule(cacheTtl, cacheTtl, cacheController, ResetTickWheel)

  val routes: Route = {
    new TransactionService().route ~
      new StatService().route ~
      SwaggerDocumentationService.routes
  }
}
