package com.github.kabdelrahman.transtat.swagger

import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import com.github.kabdelrahman.transtat.Spec

class SwaggerSpec extends Spec {

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
