package com.github.kabdelrahman.transtat.swagger

import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.kabdelrahman.transtat.Spec
import org.scalatest.{Matchers, WordSpec}

class SwaggerSpec extends WordSpec with Matchers with ScalatestRouteTest with Spec {

  "Swagger Endpoint" should {
    "returns valid API documentation" in {
      val request = HttpRequest(
        HttpMethods.GET,
        uri = "/api-docs/swagger.json")
      request ~> routes ~> check {
        status.isSuccess() shouldEqual true
      }
    }
  }
}
