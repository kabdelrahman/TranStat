package com.github.kabdelrahman.transtat.codecs

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpEntity, StatusCode}
import com.github.kabdelrahman.transtat.model.Transaction
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}

case class ErrorResponseException(responseStatus: StatusCode, response: Option[HttpEntity]) extends Exception

trait DefaultJsonFormats extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val TransactionModelFormat = new RootJsonFormat[Transaction] {
    override def read(json: JsValue): Transaction = ???
    override def write(obj: Transaction): JsValue = ???
  }

}
