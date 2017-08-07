package com.github.kabdelrahman.transtat.codecs

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpEntity, StatusCode}
import com.github.kabdelrahman.transtat.model.{Stats, Transaction}
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

  implicit val StatModelFormat = new RootJsonFormat[Stats] {

    override def read(json: JsValue): Stats = {
      val fields = json.asJsObject.fields
      val sum = fields("sum").convertTo[Double]
      val avg = fields("avg").convertTo[Double]
      val max = fields("max").convertTo[Double]
      val min = fields("min").convertTo[Double]
      val count = fields("count").convertTo[Long]
      Stats(sum = sum, avg = avg, max = max, min = min, count = count)
    }

    override def write(stat: Stats): JsValue = {
      JsObject(
        "sum" -> JsNumber(stat.sum),
        "avg" -> JsNumber(stat.avg),
        "max" -> JsNumber(stat.max),
        "min" -> JsNumber(stat.min),
        "count" -> JsNumber(stat.count)
      )
    }
  }

}
