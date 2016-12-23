package com.inocybe.poc

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.inocybe.poc.JsonProtocol._

import scala.concurrent.Future

class OrderService(system: ActorSystem)(implicit timeout: Timeout) {

  lazy val itemManager = system.actorOf(Props(classOf[ItemManager]))

  val route: Route =
    get {
      pathPrefix("item" / LongNumber) { id =>
        onComplete((itemManager ? id).asInstanceOf[Future[Item]]) { item =>
          complete(item)
        }
      }
    } ~
      post {
        path("create-order") {
          entity(as[Item]) { item =>
            onComplete((itemManager ? item).asInstanceOf[Future[String]]) { msg =>
              complete(msg)
            }
          }
        }
      }
}
