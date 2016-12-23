package com.inocybe.poc.core

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.inocybe.poc.api.OrderService

import scala.concurrent.duration._

object Boot {


  def main(args: Array[String]) {

    // needed to run the route
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    // needed for the future map/flatmap in the end
    implicit val executionContext = system.dispatcher
    implicit val timeout = Timeout(15.seconds)
    val service = new OrderService(system)

    Http().bindAndHandle(service.route, "localhost", 8080)
  }
}
