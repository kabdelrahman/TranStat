package com.github.kabdelrahman.transtat.service

import akka.http.scaladsl.model._
import akka.util.ByteString
import com.github.kabdelrahman.transtat.Spec
import com.github.kabdelrahman.transtat.model.Stats

class StatSpec extends Spec {

  "Stats Endpoint" should {
    "Empty values if no transactions" in {
      resetCache()
      val request = HttpRequest(
        HttpMethods.GET,
        uri = "/statistics")
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
        responseAs[Stats] shouldEqual Stats(0.0, 0.0, 0.0, 0.0, 0L)
      }
    }
    "Return values of one transaction if only one is posted" in {
      resetCache()
      val now = System.currentTimeMillis()
      val jsonRequest = ByteString(
        s"""
           |{
           |    "amount":12.3,
           |    "timestamp": $now
           |}
        """.stripMargin)

      var request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }

      request = HttpRequest(
        HttpMethods.GET,
        uri = "/statistics")
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
        responseAs[Stats] shouldEqual Stats(12.3, 12.3, 12.3, 12.3, 1)
      }
    }

    "Return correct statistics for all transactions" in {
      resetCache()
      val now = System.currentTimeMillis()
      var jsonRequest = ByteString(
        s"""
           |{
           |    "amount":100,
           |    "timestamp": $now
           |}
        """.stripMargin)

      var request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }

      jsonRequest = ByteString(
        s"""
           |{
           |    "amount":200,
           |    "timestamp": $now
           |}
        """.stripMargin)

      request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }

      jsonRequest = ByteString(
        s"""
           |{
           |    "amount":300,
           |    "timestamp": $now
           |}
        """.stripMargin)

      request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }

      request = HttpRequest(
        HttpMethods.GET,
        uri = "/statistics")
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
        responseAs[Stats] shouldEqual Stats(600, 200, 300, 100, 3)
      }
    }
    "Return correct statistics for all transactions with proper min/max values" in {
      resetCache()
      val now = System.currentTimeMillis()
      var jsonRequest = ByteString(
        s"""
           |{
           |    "amount":100,
           |    "timestamp": $now
           |}
        """.stripMargin)

      var request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }

      jsonRequest = ByteString(
        s"""
           |{
           |    "amount":200,
           |    "timestamp": $now
           |}
        """.stripMargin)

      request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }

      jsonRequest = ByteString(
        s"""
           |{
           |    "amount":300,
           |    "timestamp": $now
           |}
        """.stripMargin)

      request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }

      jsonRequest = ByteString(
        s"""
           |{
           |    "amount":50,
           |    "timestamp": $now
           |}
        """.stripMargin)

      request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }

      jsonRequest = ByteString(
        s"""
           |{
           |    "amount":500,
           |    "timestamp": $now
           |}
        """.stripMargin)

      request = HttpRequest(
        HttpMethods.POST,
        uri = "/transactions",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
      }

      request = HttpRequest(
        HttpMethods.GET,
        uri = "/statistics")
      request ~> routes ~> check {
        response.status shouldEqual StatusCodes.OK
        responseAs[Stats] shouldEqual Stats(1150, 230, 500, 50, 5)
      }
    }
  }
}
