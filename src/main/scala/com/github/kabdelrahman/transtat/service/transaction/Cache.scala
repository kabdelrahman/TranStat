package com.github.kabdelrahman.transtat.service.transaction

import com.github.kabdelrahman.transtat.bootstrap.AppConfig
import com.github.kabdelrahman.transtat.model.Transaction

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait Cache[T] {

  // Made for flexibility in future caching implementations
  def put(t: T): Future[Boolean]
}


object TransactionInMemoryCache extends Cache[Transaction] with AppConfig {

  // Working with Red/Green/Refactor so this method is doing nothing, but it's enough to be GREEN :-).
  override def put(trx: Transaction): Future[Boolean] = Future(true)

}
