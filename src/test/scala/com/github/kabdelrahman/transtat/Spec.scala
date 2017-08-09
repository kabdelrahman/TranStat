package com.github.kabdelrahman.transtat

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.kabdelrahman.transtat.api.{Api, Core}
import com.github.kabdelrahman.transtat.codecs.DefaultJsonFormats
import com.github.kabdelrahman.transtat.persistence.ResetCache
import org.scalatest.{Matchers, WordSpec}

trait Spec extends WordSpec with Matchers with ScalatestRouteTest with Core with Api with DefaultJsonFormats {
  def resetCache(): Unit = {
    cacheController ! ResetCache()
  }
}
