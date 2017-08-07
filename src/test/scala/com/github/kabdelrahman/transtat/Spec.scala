package com.github.kabdelrahman.transtat

import com.github.kabdelrahman.transtat.api.{Api, Core}
import com.github.kabdelrahman.transtat.codecs.DefaultJsonFormats
import com.github.kabdelrahman.transtat.service.TransactionInMemoryCache
import com.typesafe.scalalogging.LazyLogging

trait Spec extends LazyLogging with Core with Api with DefaultJsonFormats {
  implicit lazy val cache = new TransactionInMemoryCache
}
