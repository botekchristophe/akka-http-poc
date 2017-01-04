package com.inocybe.poc.core

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.inocybe.poc.api.ItemService

import scala.concurrent.duration._

object Boot {


  def main(args: Array[String]) {

    implicit val system = ActorSystem()

    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    implicit val timeout = Timeout(15.seconds)

    ActorRegistry.init(system)

    val service = new ItemService(system)

    Http().bindAndHandle(service.route, "localhost", 8080)
  }
}
