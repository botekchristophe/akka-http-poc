package com.inocybe.poc.controler

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.pattern.ask
import com.inocybe.poc.api.Service.{ItemCreated, ItemInfo, ItemsInfo}
import com.inocybe.poc.core.ActorRegistry
import com.inocybe.poc.outbound.GitlabConnector
import spray.json.JsonParser
import com.inocybe.poc.model.JsonProtocol._
import com.inocybe.poc.model.exception.ServiceException
import com.inocybe.poc.model.gitlab.GlProject
import com.inocybe.poc.model.internal.Item
import spray.json._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class ItemManager extends Controler {


  val items = scala.collection.mutable.HashMap.empty[Long, String]
  lazy val gitlabConnector = ActorRegistry.getActor(classOf[GitlabConnector], context)

  override def receive: Receive = {
    case id: Long                   =>
      items.get(id).isDefined match {
        case true   => sender ! ItemInfo(Item(id, items(id)))
        case false  => sender ! ServiceException(StatusCodes.NotFound, "Id not found.", s"No item with id=$id.")
      }

    case i: Item                    =>
      items.put(i.id, i.name)
      sender ! ItemCreated(i)

    case "repository"               =>
      val repo = Await.result(gitlabConnector ? GitlabConnector.GetRepositories, 5 seconds)
        .asInstanceOf[HttpResponse]
        .convertTo[List[GlProject]]
      sender ! ItemsInfo(repo)

  }
}
