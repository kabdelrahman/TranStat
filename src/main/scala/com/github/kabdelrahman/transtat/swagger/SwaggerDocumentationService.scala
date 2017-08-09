package com.github.kabdelrahman.transtat.swagger

import com.github.kabdelrahman.transtat.bootstrap.AppConfig
import com.github.kabdelrahman.transtat.service.stats.StatService
import com.github.kabdelrahman.transtat.service.transaction.TransactionService
import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import io.swagger.models.auth.BasicAuthDefinition

object SwaggerDocumentationService extends SwaggerHttpService with AppConfig {
  override val apiClasses = Set(classOf[TransactionService], classOf[StatService])
  override val host = s"$httpHost:$httpPort"
  override val info = Info(
    title = "TranStat",
    description = "The main use case for our API is to calculate realtime statistic from the last 60 seconds. " +
      "There will be two APIs, one of them is called every time a transaction is made. " +
      "It is also the sole input of this rest API. " +
      "The other one returns the statistic based of the transactions of the last 60 seconds.",
    version = version)
  override val securitySchemeDefinitions = Map("basicAuth" -> new BasicAuthDefinition())
}
