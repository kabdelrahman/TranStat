package com.github.kabdelrahman.transtat.service

import akka.http.scaladsl.model._
import akka.util.ByteString
import com.github.kabdelrahman.transtat.Spec

import scala.util.Random


class TransactionSpec extends Spec {

  "Transactions Endpoint" should {
    "return `200` if transaction added successfully" in {
      resetCache()
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
      resetCache()
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
      resetCache()
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

    "return `204` if transaction time was in the future (threshold of 5 milliseconds)" in {
      resetCache()
      val nowPlus5Mills = System.currentTimeMillis() + 500
      val jsonRequest = ByteString(
        s"""
           |{
           |    "amount":12.3,
           |    "timestamp": $nowPlus5Mills
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
