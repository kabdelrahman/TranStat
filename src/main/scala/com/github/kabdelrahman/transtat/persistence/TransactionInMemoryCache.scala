package com.github.kabdelrahman.transtat.persistence

import com.github.kabdelrahman.transtat.bootstrap.AppConfig._
import com.github.kabdelrahman.transtat.model.Transaction

import scala.collection.concurrent.TrieMap

class TransactionInMemoryCache extends Cache[Transaction] {

  // Initialize expected cache size
  data.sizeHint(cacheTtl.toMillis.toInt)
  dataTransit.sizeHint(cacheTtl.toMillis.toInt)

  // ONLY FOR TESTING PURPOSES
  def resetCache(): Unit = {
    data.clear()
    dataTransit.clear()
    resetStats()
  }

  override def put(trx: Transaction): Unit = {
    putInMapAndCalculateStats(trx, data)
  }

  private def putInMapAndCalculateStats(trx: Transaction, map: TrieMap[Long, Vector[Transaction]]): Unit = {
    if (map.contains(trx.timestamp)) {
      map += (trx.timestamp -> (map(trx.timestamp) :+ trx))
    }
    else {
      map += (trx.timestamp -> Vector(trx))
    }
    calculateStats(trx)
  }

  // TODO calculation should be thread-safe in a way.
  private def calculateStats(trx: Transaction) = {
    count += 1
    sum += trx.amount
    avg = sum / count

    if (min == 0) {
      min = trx.amount
    }
    else if (min > trx.amount) {
      min = trx.amount
    }

    if (max < trx.amount) {
      max = trx.amount
    }
  }

  override def wipeHistoryAndCalculateStatsBefore(time: Long): Unit = {
    resetStats()
    // TODO parallelize the caching and statistics calculation
    data.dropWhile(trx => time > trx._1).foreach(_._2.foreach(putInMapAndCalculateStats(_, dataTransit)))
    data = dataTransit.clone()
    dataTransit.clear()

  }

  override def resetStats(): Unit = {
    sum = 0.0
    max = 0.0
    min = 0.0
    avg = 0.0
    count = 0L
  }
}
