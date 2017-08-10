package com.github.kabdelrahman.transtat.codecs

import com.github.kabdelrahman.transtat.model.{Stats, Transaction}
import org.scalatest.{Matchers, WordSpec}
import spray.json._

class DefaultJsonFormatsSpec extends WordSpec with Matchers with DefaultJsonFormats {

  "DefaultJsonFormats" should {
    "convert transaction json to transaction object and from transaction object to json again" in {
      val now = System.currentTimeMillis()
      val json =
        s"""
           |{
           | "amount": 10.2,
           | "timestamp": $now
           |}
        """.stripMargin

      val transaction = Transaction(10.2, now)
      json.parseJson.convertTo[Transaction] shouldEqual transaction
      transaction.toJson shouldEqual json.parseJson
    }
    "convert stats json to stats object and from stats object to json again" in {
      val now = System.currentTimeMillis()
      val json =
        s"""
           |{
           | "sum": 10.2,
           | "min": 11.2,
           | "max": 13.2,
           | "avg": 6.1,
           | "count": 10
           |}
        """.stripMargin

      val stats = Stats(10.2,6.1,13.2,11.2,10)
      json.parseJson.convertTo[Stats] shouldEqual stats
      stats.toJson shouldEqual json.parseJson
    }
  }
}
