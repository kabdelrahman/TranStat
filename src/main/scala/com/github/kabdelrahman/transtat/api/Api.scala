package com.github.kabdelrahman.transtat.api

import akka.http.scaladsl.server.{Route, RouteConcatenation}
import com.github.kabdelrahman.transtat.Metrics.Metrics
import com.github.kabdelrahman.transtat.service.transaction.TransactionService
import com.github.kabdelrahman.transtat.swagger.SwaggerDocumentationService

trait Api extends RouteConcatenation {
  this: CoreActors with Core =>

  private implicit val _ = system.dispatcher

  implicit val metrics: Metrics

  val routes: Route = {
    new TransactionService(transactionActor).route ~
      SwaggerDocumentationService.routes
  }
}
