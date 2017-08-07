package com.github.kabdelrahman.transtat

import com.github.kabdelrahman.transtat.api.{Api, Core}
import com.typesafe.scalalogging.LazyLogging

trait Spec extends LazyLogging with Core with Api
