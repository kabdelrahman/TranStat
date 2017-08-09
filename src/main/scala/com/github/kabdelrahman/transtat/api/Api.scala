package com.github.kabdelrahman.transtat.api

import akka.actor.ActorRef
import akka.http.scaladsl.server.{Route, RouteConcatenation}
import com.github.kabdelrahman.transtat.metrics.AppWideMetrics
import com.github.kabdelrahman.transtat.persistence.{CacheController, ResetTickWheel, Tick}
import com.github.kabdelrahman.transtat.service.stats.StatService
import com.github.kabdelrahman.transtat.service.transaction.TransactionService
import com.github.kabdelrahman.transtat.swagger.SwaggerDocumentationService

import scala.concurrent.duration._

trait Api extends RouteConcatenation {
  this: Core =>

  private implicit val _ = system.dispatcher

  implicit val metrics = AppWideMetrics

  implicit val cacheController: ActorRef = system.actorOf(CacheController())

  // non-cancellable scheduler for cache controlling (ticking every 1 second to refresh cache)
  system.scheduler.schedule(0.seconds, 1.second, cacheController, Tick)
  system.scheduler.schedule(60.seconds, 60.seconds, cacheController, ResetTickWheel)

  val routes: Route = {
    new TransactionService().route ~
      new StatService().route ~
      SwaggerDocumentationService.routes
  }
}
