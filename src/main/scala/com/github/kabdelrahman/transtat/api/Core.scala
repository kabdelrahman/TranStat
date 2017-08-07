package com.github.kabdelrahman.transtat.api

import akka.actor.{ActorRef, ActorSystem, Props}
import com.github.kabdelrahman.transtat.service.transaction.TransactionActor

trait Core {
  implicit def system: ActorSystem
}

trait CoreActorSystem extends Core {
  override val system = ActorSystem("main-transtat-actor-system")
  sys.addShutdownHook(system.terminate())
}
