package com.github.kabdelrahman.transtat.api

import akka.actor.ActorSystem

trait Core {
  implicit def system: ActorSystem
}

trait CoreActorSystem extends Core {
  override val system = ActorSystem("main-transtat-actor-system")
  sys.addShutdownHook(system.terminate())
}
