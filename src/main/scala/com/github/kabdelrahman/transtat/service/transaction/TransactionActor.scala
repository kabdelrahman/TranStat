package com.github.kabdelrahman.transtat.service.transaction

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.event.LoggingReceive
import com.github.kabdelrahman.transtat.bootstrap.AppConfig
import com.github.kabdelrahman.transtat.model.Transaction
import com.github.kabdelrahman.transtat.persistence.CacheRequest

case class TransactionOpResponse(successful: Boolean)


object TransactionActor {
  def apply(cacheController: ActorRef): Props = Props(new TransactionActor(cacheController))
}

class TransactionActor(cacheController: ActorRef) extends Actor with ActorLogging with AppConfig {

  def receive: Receive = LoggingReceive {
    case trx: Transaction =>
      val originalSender = sender()
      val now = System.currentTimeMillis()
      if (trx.timestamp > now) {
        originalSender ! TransactionOpResponse(false)
      } else {
        val delta = now - trx.timestamp
        // TODO move `cacheTtl` to be passed to the Actor itself with the cacheController Info.
        if (delta <= cacheTtl.toMillis) {
          cacheController ! CacheRequest(trx)
          originalSender ! TransactionOpResponse(true)
        }
        else {
          originalSender ! TransactionOpResponse(false)
        }
      }
      context.stop(self)
  }
}

