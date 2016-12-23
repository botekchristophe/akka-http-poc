package com.inocybe.poc.api

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.inocybe.poc.api.Service.{ItemCreated, ItemInfo}
import com.inocybe.poc.controler.ItemManager
import com.inocybe.poc.model.Item
import com.inocybe.poc.model.JsonProtocol._

import scala.concurrent.Future

class OrderService(system: ActorSystem)(implicit timeout: Timeout) extends Service {

  lazy val itemManager = system.actorOf(Props(classOf[ItemManager]))

  val route: Route =
    get {
      pathPrefix("item" / LongNumber) { id =>
        onComplete((itemManager ? id).asInstanceOf[Future[ItemCreated]]) { item =>
          futureHandler(item)
        }
      }
    } ~
      post {
        path("create-order") {
          entity(as[Item]) { item =>
            onComplete((itemManager ? item).asInstanceOf[Future[ItemInfo]]) { msg =>
              futureHandler(msg)
            }
          }
        }
      }
}
