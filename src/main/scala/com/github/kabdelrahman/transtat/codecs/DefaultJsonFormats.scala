package com.github.kabdelrahman.transtat.codecs

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpEntity, StatusCode}
import com.github.kabdelrahman.transtat.model.Transaction
import spray.json.{DefaultJsonProtocol, JsNumber, JsObject, JsValue, RootJsonFormat}

case class ErrorResponseException(responseStatus: StatusCode, response: Option[HttpEntity]) extends Exception

trait DefaultJsonFormats extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val TransactionModelFormat = new RootJsonFormat[Transaction] {
    override def read(json: JsValue): Transaction = {
      val fields = json.asJsObject.fields
      //TODO Harden the parsing to handle missing fields
      val amount: Double = fields("amount").convertTo[Double]
      val timestamp = fields("timestamp").convertTo[Long]
      Transaction(amount, timestamp)
    }

    override def write(transaction: Transaction): JsValue = {
      JsObject(
        "amount" -> JsNumber(transaction.amount),
        "timestmap" -> JsNumber(transaction.timestamp)
      )
    }
  }

}
