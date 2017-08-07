package com.github.kabdelrahman.transtat.service

import com.github.kabdelrahman.transtat.bootstrap.AppConfig
import com.github.kabdelrahman.transtat.model.Transaction

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Cache[T] {

  var sum: Double = 0.0
  var avg: Double = 0.0
  var max: Double = 0.0
  var min: Double = 0.0
  var count: Long = 0L

  // Made for flexibility in future caching implementations
  def put(t: T): Future[Boolean]
}


class TransactionInMemoryCache extends Cache[Transaction] with AppConfig {


  override def put(trx: Transaction): Future[Boolean] = {
    // TODO move cached data calculation into StatService
    // We could move this calculation logic to StatService in case we have other complex calculations (ex. historical or multi-user calculations),
    // However, based on the current scope, this implementation should be okay >:-).
    count += 1
    sum += trx.amount
    avg = sum / count
    if (min == 0) {
      min = trx.amount
    }
    else if (min > trx.amount) {
      min = trx.amount
    }
    if (max == 0) {
      max = trx.amount
    }
    else if (max < trx.amount) {
      max = trx.amount
    }
    Future(true)
  }

}
