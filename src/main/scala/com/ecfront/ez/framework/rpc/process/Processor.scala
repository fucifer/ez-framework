package com.ecfront.ez.framework.rpc.process

import com.typesafe.scalalogging.slf4j.LazyLogging
import io.vertx.core.Vertx

/**
 * 处理器接口，用于处理各个通道的服务与连接
 */
trait Processor extends LazyLogging {

  protected val FLAG_METHOD: String = "__method__"
  protected val FLAG_PATH: String = "__path__"
  protected val FLAG_INTERCEPTOR_INFO: String = "__InterceptorInfo__"
  protected var port: Int = _
  protected var host: String = _

  protected val vertx = Vertx.vertx()

  /**
   * 初始化
   */
  protected def init()

  /**
   * 销毁服务
   */
  private[rpc] def destroy()


}
