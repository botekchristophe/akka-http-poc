package com.inocybe.poc.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.inocybe.poc.api.Service.{ItemCreated, ItemInfo}
import com.inocybe.poc.controler.ItemManager
import com.inocybe.poc.core.ActorRegistry
import com.inocybe.poc.model.JsonProtocol._
import com.inocybe.poc.model.internal.Item
import spray.json._

import scala.concurrent.Future

class OrderService(system: ActorSystem)(implicit timeout: Timeout) extends Service {

  lazy val itemManager = ActorRegistry.getActor(classOf[ItemManager], system)

  val route: Route =
    get {
      pathPrefix("repository") {
        onComplete((itemManager ? "repository").asInstanceOf[Future[ItemInfo]]) { item =>
          futureHandler(item)
        }
      }
    }
}
