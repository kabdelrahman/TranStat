package com.github.kabdelrahman.transtat.service.transaction

import akka.actor.{Actor, ActorLogging, Props}
import akka.event.LoggingReceive
import com.github.kabdelrahman.transtat.bootstrap.AppConfig
import com.github.kabdelrahman.transtat.model.Transaction
import com.github.kabdelrahman.transtat.service.Cache

import scala.concurrent.ExecutionContext.Implicits.global

case class TransactionOpResponse(successful: Boolean)


object TransactionActor {
  def apply(cache: Cache[Transaction]): Props = Props(new TransactionActor(cache))
}

class TransactionActor(cache: Cache[Transaction]) extends Actor with ActorLogging with AppConfig {

  def receive: Receive = LoggingReceive {
    case trx: Transaction =>
      val originalSender = sender()
      val now = System.currentTimeMillis()
      val delta = now - trx.timestamp
      if (delta <= ttl.toMillis) {
        cache.put(trx).map(originalSender ! TransactionOpResponse(_))
      }
      else {
        originalSender ! TransactionOpResponse(false)
      }

      context.stop(self)
  }
}

