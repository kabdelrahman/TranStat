package com.github.kabdelrahman.transtat.model

case class Transaction(amount: Double, timestamp: Long)

case class Stats(sum: Double, avg: Double, max: Double, min: Double, count: Long)
