package com.github.kabdelrahman.transtat.api

import akka.http.scaladsl.server.{Route, RouteConcatenation}
import com.github.kabdelrahman.transtat.metrics.AppWideMetrics
import com.github.kabdelrahman.transtat.service.transaction.TransactionService
import com.github.kabdelrahman.transtat.swagger.SwaggerDocumentationService

trait Api extends RouteConcatenation {
  this: Core =>

  private implicit val _ = system.dispatcher

  implicit val metrics = AppWideMetrics

  val routes: Route = {
    new TransactionService().route ~
      SwaggerDocumentationService.routes
  }
}
