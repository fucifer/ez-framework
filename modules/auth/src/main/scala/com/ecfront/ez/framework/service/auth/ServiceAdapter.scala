package com.ecfront.ez.framework.service.auth

import com.ecfront.common.Resp
import com.ecfront.ez.framework.core.EZServiceAdapter
import com.ecfront.ez.framework.core.interceptor.EZAsyncInterceptorProcessor
import com.ecfront.ez.framework.service.rpc.foundation.AutoBuildingProcessor
import com.ecfront.ez.framework.service.rpc.http.{HTTP, HttpInterceptor}
import io.vertx.core.json.JsonObject

object ServiceAdapter extends EZServiceAdapter[JsonObject] {

  var publicUriPrefix: String = "/public/"
  var allowRegister: Boolean = false
  var loginUrl: String = ""

  override def init(parameter: JsonObject): Resp[String] = {
    publicUriPrefix = parameter.getString("publicUriPrefix", "/public/")
    allowRegister = parameter.getBoolean("allowRegister", false)
    loginUrl = parameter.getString("loginUrl", "#/auth/login")
    if (!loginUrl.toLowerCase().startsWith("http")) {
      loginUrl = com.ecfront.ez.framework.service.rpc.http.ServiceAdapter.webUrl + loginUrl
    }
    EZAsyncInterceptorProcessor.register(HttpInterceptor.category, AuthHttpInterceptor)
    AutoBuildingProcessor.autoBuilding[HTTP]("com.ecfront.ez.framework.service.auth", classOf[HTTP])
    Initiator.init()
    Resp.success("")
  }

  override def destroy(parameter: JsonObject): Resp[String] = {
    Resp.success("")
  }

  override lazy val dependents: collection.mutable.Set[String] = collection.mutable.Set(
    com.ecfront.ez.framework.service.rpc.http.ServiceAdapter.serviceName,
    com.ecfront.ez.framework.service.storage.mongo.ServiceAdapter.serviceName,
    com.ecfront.ez.framework.service.redis.ServiceAdapter.serviceName,
    com.ecfront.ez.framework.service.email.ServiceAdapter.serviceName
  )

  override var serviceName: String = "auth"

}

