package com.github.kabdelrahman.transtat.service

import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.github.kabdelrahman.transtat.Spec
import org.scalatest.{Matchers, WordSpec}

import scala.util.Random


class TransactionSpec extends WordSpec with Matchers with ScalatestRouteTest with Spec {

  "Transactions Endpoint" should {
    "return `200` if transaction added successfully" in {
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
    "still return `200` if transaction time is done within the last 60 seconds" in {
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
    "return `204` if transaction time is older than 60 seconds" in {
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
