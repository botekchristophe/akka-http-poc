package com.inocybe.poc.outbound

import akka.actor.Actor
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpHeader, HttpMethod, HttpRequest, Uri}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.inocybe.poc.core.GlobalConfiguration

trait OutboundConnector extends Actor with GlobalConfiguration {
  import akka.pattern.pipe
  import context.dispatcher
  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))

  def auth: HttpHeader = ???

  def exec(method: HttpMethod, url: String) = {
    val request = HttpRequest(method, Uri(url), headers = List(auth))
    Http(context.system).singleRequest(request).pipeTo(sender)
  }
}
