package com.github.kabdelrahman.transtat.persistence

import akka.actor.{Actor, ActorLogging, Props}
import akka.event.LoggingReceive
import com.github.kabdelrahman.transtat.bootstrap.AppConfig
import com.github.kabdelrahman.transtat.model.{Stats, Transaction}

case class CacheRequest(trx: Transaction)

case class GetStats()

case class Tick()

case class ResetTickWheel()

// TEST-ONLY
case class ResetCache()

object CacheController {
  def apply(): Props = Props[CacheController]
}

class CacheController extends Actor with ActorLogging with AppConfig {
  private val cache = new TransactionInMemoryCache
  private var tickWheelTime = 0L

  override def receive: Receive = LoggingReceive {
    case ResetCache() =>
      cache.resetCache()
    case GetStats() =>
      val originalSender = sender()
      originalSender ! Stats(cache.sum, cache.avg, cache.max, cache.min, cache.count)
    case CacheRequest(trx) =>
      cache.put(trx)
    case ResetTickWheel() =>
      tickWheelTime = getCurrentTime
    case Tick() =>
      if (tickWheelTime == 0L) {
        tickWheelTime = getCurrentTime
      }
      cache.wipeHistoryAndCalculateStatsBefore(tickWheelTime)
  }

  private def getCurrentTime: Long = System.currentTimeMillis()
}
