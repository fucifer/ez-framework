package com.ecfront.ez.framework.service.jdbc

import com.ecfront.common.Resp
import com.ecfront.ez.framework.core.EZServiceAdapter
import com.fasterxml.jackson.databind.JsonNode

object ServiceAdapter extends EZServiceAdapter[JsonNode] {

  var createTable:Boolean=false

  override def init(parameter: JsonNode): Resp[String] = {
    createTable=parameter.path("createTable").asBoolean(false)
    val processor = JDBCProcessor(
      parameter.path("url").asText(),
      parameter.path("userName").asText(),
      parameter.path("password").asText()
    )
    if (parameter.has("initialSize")) {
      processor.setInitialSize(parameter.path("initialSize").asInt())
    }
    if (parameter.has("minSize")) {
      processor.setMinSize(parameter.path("minSize").asInt())
    }
    if (parameter.has("maxSize")) {
      processor.setMaxSize(parameter.path("maxSize").asInt())
    }
    if (parameter.has("maxIdleTime")) {
      processor.setMaxIdleTime(parameter.path("maxIdleTime").asInt())
    } else {
      processor.setMaxIdleTime(18000)
    }
    val result = JDBCProcessor.initDS(processor)
    if (parameter.has("package")) {
      EntityContainer.autoBuilding(parameter.get("package").asText())
    }
    result
  }

  override def destroy(parameter: JsonNode): Resp[String] = {
    JDBCProcessor.close()
    Resp.success("")
  }

  override var serviceName: String = "jdbc"

}


