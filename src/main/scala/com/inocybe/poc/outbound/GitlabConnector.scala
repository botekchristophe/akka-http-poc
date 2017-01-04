package com.inocybe.poc.outbound

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import com.inocybe.poc.outbound.GitlabConnector.GetRepositories


object GitlabConnector {
  case object GetRepositories
}
class GitlabConnector extends OutboundConnector {

  override def auth: RawHeader = RawHeader("PRIVATE-TOKEN", glApiKey)

  override def receive: Receive = {
    case GetRepositories => exec(HttpMethods.GET, glProjectUrl)
  }
}
