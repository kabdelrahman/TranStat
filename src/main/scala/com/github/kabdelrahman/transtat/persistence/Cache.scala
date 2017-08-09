package com.github.kabdelrahman.transtat.persistence

import scala.collection.concurrent.TrieMap


// TODO move calculations from Cache to another trait/class for better Separation of Concerns
trait Cache[T] {

  // TODO have a better model for statistics.
  protected[persistence] var sum: Double = 0.0
  protected[persistence] var avg: Double = 0.0
  protected[persistence] var max: Double = 0.0
  protected[persistence] var min: Double = 0.0
  protected[persistence] var count: Long = 0L

  protected[persistence] def put(t: T): Unit

  protected[persistence] def wipeHistoryAndCalculateStatsBefore(time: Long): Unit

  protected[persistence] def resetStats(): Unit

  // The purpose of having a TrieMap is to have a maximum of (default 60000 entries represent 60 seconds in milliseconds)
  protected[persistence] var data: TrieMap[Long, Vector[T]] = TrieMap.empty[Long, Vector[T]]
  protected[persistence] var dataTransit: TrieMap[Long, Vector[T]] = TrieMap.empty[Long, Vector[T]]
}



