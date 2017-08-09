package com.github.kabdelrahman.transtat.persistence

import com.github.kabdelrahman.transtat.model.Transaction
import org.scalatest.{Matchers, WordSpec}

import scala.collection.concurrent.TrieMap

class TransactionInMemoryCacheSpec extends WordSpec with Matchers {


  val now = System.currentTimeMillis()
  "TransactionInMemoryCache" should {
    "Add transactions in data when put is called, transactions should be grouped if they have the same time" in {
      val cache = new TransactionInMemoryCache
      cache.put(Transaction(10, now))
      cache.put(Transaction(11, now))
      cache.data.size shouldBe 1
      cache.data.values.flatten.size shouldBe 2
    }
    "Add transactions in data when put is called, transactions should not be grouped if they don't have the same time" in {
      val cache = new TransactionInMemoryCache
      cache.put(Transaction(10, now))
      cache.put(Transaction(11, now+1))
      cache.data.size shouldBe 2
      cache.data.values.flatten.size shouldBe 2
    }
    "Wipe history before certain point of time" in {
      // Adding 4 transactions and then wipe 2 of them using time
      val cache = new TransactionInMemoryCache
      cache.put(Transaction(1, now - 300))
      cache.put(Transaction(2, now - 200))
      cache.put(Transaction(3, now - 10))
      cache.put(Transaction(4, now - 20))
      cache.data.size shouldBe 4
      cache.wipeHistoryAndCalculateStatsBefore(now - 50)
      cache.data.size shouldBe 2
      cache.data shouldBe TrieMap[Long, Vector[Transaction]](now - 10 -> Vector(Transaction(3, now - 10)), now - 20 -> Vector(Transaction(4, now - 20)))
    }
  }
}
