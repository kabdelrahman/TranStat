package com.github.kabdelrahman.transtat.service.stats

import javax.ws.rs.Path

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern._
import akka.util.Timeout
import com.github.kabdelrahman.transtat.bootstrap.AppConfig
import com.github.kabdelrahman.transtat.codecs.DefaultJsonFormats
import com.github.kabdelrahman.transtat.metrics.Metrics
import com.github.kabdelrahman.transtat.metrics.TimerSupport._
import com.github.kabdelrahman.transtat.model.Stats
import com.github.kabdelrahman.transtat.persistence.GetStats
import io.swagger.annotations.{Api, ApiOperation, ApiResponse, ApiResponses}
import spray.json.RootJsonFormat

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


@Api(
  value = "/statistics",
  description = "Statistics service have to execute in constant time and memory (O(1)). " +
    "It returns the statistic based on the transactions which happened in the last 60 seconds.",
  produces = "application/json")
@Path("/statistics")
class StatService()(implicit executionContext: ExecutionContext,
                    implicit val metrics: Metrics,
                    implicit val system: ActorSystem,
                    implicit val cacheController: ActorRef
)
  extends Directives with DefaultJsonFormats with AppConfig {

  // That's a very high timeout that should be monitored by metrics and reduced overtime.
  implicit val timeout = Timeout(2.seconds)

  implicit val statsModelFormat: RootJsonFormat[Stats] = jsonFormat5(Stats)

  val route: Route = getStats

  @ApiOperation(
    value =
      "Returns statistics about transactions (sum, min, max, avg and count)",
    httpMethod = "GET")
  @ApiResponses(Array(
    new ApiResponse(code = 200, response = classOf[Stats], message = ""),
  ))
  def getStats: Route =
    path("statistics") {
      get {
        timeit(metrics.statsMetrics) {
          complete {
            (cacheController ? GetStats()).mapTo[Stats]
          }
        }
      }
    }
}






