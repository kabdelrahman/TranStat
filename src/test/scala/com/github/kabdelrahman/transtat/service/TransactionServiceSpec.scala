package com.github.kabdelrahman.transtat.service

import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.github.kabdelrahman.transtat.api.{Api, Core, CoreActors}
import com.github.kabdelrahman.transtat.metrics.{AppWideMetrics, Metrics}
import org.scalatest.{Matchers, WordSpec}

import scala.util.Random

class TransactionServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with Api with Core with CoreActors {

  override implicit val metrics: Metrics = AppWideMetrics


  "Transactions Endpoint" should {
    "return `200` if transaction added successfully" ignore {
      val now = System.currentTimeMillis()
      val jsonRequest = ByteString(
        s"""
           |{
           |    "amount":12.3,
           |    "timestamp": $now
           |}
        """.stripMargin)

      val request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }
    }
    "still return `200` if transaction time is done within the last 60 seconds" ignore {
      val now = System.currentTimeMillis() - Random.nextInt(60000)
      val jsonRequest = ByteString(
        s"""
           |{
           |    "amount":12.3,
           |    "timestamp": $now
           |}
        """.stripMargin)
      val request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }
    }
    "return `204` if transaction time is older than 60 seconds" ignore {
      val now = System.currentTimeMillis() - 60010
      val jsonRequest = ByteString(
        s"""
           |{
           |    "amount":12.3,
           |    "timestamp": $now
           |}
        """.stripMargin)
      val request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.NoContent
      }
    }
  }
}
