package com.github.kabdelrahman.transtat.service.stats

import akka.actor.{Actor, ActorLogging, Props}
import akka.event.LoggingReceive
import com.github.kabdelrahman.transtat.bootstrap.AppConfig
import com.github.kabdelrahman.transtat.model.{Stats, Transaction}
import com.github.kabdelrahman.transtat.service.Cache

case class GetStats()

object StatsActor {
  def apply(cache: Cache[Transaction]): Props = Props(new StatsActor(cache))
}

class StatsActor(cache: Cache[Transaction]) extends Actor with ActorLogging with AppConfig {

  def receive: Receive = LoggingReceive {
    case _: GetStats =>
    val originalSender = sender()
    originalSender ! Stats(cache.sum, cache.avg, cache.max, cache.min, cache.count)
    context.stop(self)
  }
}

