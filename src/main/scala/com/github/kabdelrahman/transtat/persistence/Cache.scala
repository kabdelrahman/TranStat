package com.github.kabdelrahman.transtat.persistence

import com.github.kabdelrahman.transtat.bootstrap.AppConfig

import scala.collection.concurrent.TrieMap


// TODO move calculations from Cache to another trait/class for better Separation of Concerns
trait Cache[T] {

  // TODO have a better model for statistics.
  var sum: Double = 0.0
  var avg: Double = 0.0
  var max: Double = 0.0
  var min: Double = 0.0
  var count: Long = 0L

  def put(t: T): Unit

  def wipeHistoryAndCalculateStatsBefore(time: Long): Unit

  def resetStats(): Unit

  // The purpose of having a TrieMap is to have a maximum of (default 60000 entries represent 60 seconds in milliseconds)
  protected var data: TrieMap[Long, Vector[T]] = TrieMap.empty[Long, Vector[T]]
  protected var dataTransit: TrieMap[Long, Vector[T]] = TrieMap.empty[Long, Vector[T]]
}



