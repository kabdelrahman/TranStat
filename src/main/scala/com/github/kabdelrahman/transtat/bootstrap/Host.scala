package com.github.kabdelrahman.transtat.bootstrap

import akka.actor.ActorLogging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.github.kabdelrahman.transtat.api.{Api, Core, CoreActors}
import com.typesafe.scalalogging.LazyLogging

trait Host {
  this: AppConfig with Api with CoreActors with Core with LazyLogging =>
  implicit val mat = ActorMaterializer()
  Http().bindAndHandle(routes, httpHost, httpPort)
  logger.info(s"application started on $httpHost:$httpPort")
}
