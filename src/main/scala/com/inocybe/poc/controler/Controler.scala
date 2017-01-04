package com.inocybe.poc.controler

import java.nio.charset.Charset

import akka.actor.Actor
import akka.http.scaladsl.model.HttpResponse
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.Timeout
import com.inocybe.poc.core.GlobalConfiguration
import spray.json.JsonParser
import com.inocybe.poc.model.JsonProtocol._
import spray.json._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

trait Controler extends Actor with GlobalConfiguration {
  implicit val timeout = Timeout(15.seconds)
  import context.dispatcher
  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))


  implicit class HttpResponseConverter(response: HttpResponse) {
    def convertTo[S :JsonReader]: S = {
      val data = Await.result(response.entity.toStrict(3000.millis).map{ entity =>
        Charset.forName("UTF-8").decode(entity.data.asByteBuffer).toString
      }, Duration.Inf)
      JsonParser(data).convertTo[S]
    }
  }
}
