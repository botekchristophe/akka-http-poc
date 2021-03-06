package com.inocybe.poc.model.exception

import akka.http.scaladsl.model.StatusCode
import com.inocybe.poc.model.ModelObject

case class ErrorDetail(code: Int, error: String, message: String, info: String) extends ModelObject
case class ServiceException(code: StatusCode,
                            msg: String,
                            info: String) extends RuntimeException(msg) {
  def marshall: ErrorDetail = {
    ErrorDetail(
      this.code.intValue(),
      this.code.reason(),
      msg,
      info
    )
  }
}
