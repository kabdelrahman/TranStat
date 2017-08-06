package com.github.kabdelrahman.transtat.service.transaction

import akka.actor.{Actor, ActorLogging, Props}
import akka.event.LoggingReceive
import com.github.kabdelrahman.transtat.model.Transaction

object TransactionActor {
  def apply(): Props = Props[TransactionActor]
}

class TransactionActor extends Actor with ActorLogging {

  def receive: Receive = LoggingReceive {
    case trans: Transaction =>
      sender ! "Received " + trans.toString
  }
}
