package com.inocybe.poc.controler

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.pattern.ask
import com.inocybe.poc.api.Service.{ItemCreated, ItemInfo, ItemsInfo}
import com.inocybe.poc.controler.ItemManager.{CreateItem, GetItem, GetItems}
import com.inocybe.poc.core.ActorRegistry
import com.inocybe.poc.datastore.Vertices
import com.inocybe.poc.datastore.neo4j.NeoGraph
import com.inocybe.poc.model.JsonProtocol._
import com.inocybe.poc.model.exception.ServiceException
import com.inocybe.poc.model.gitlab.GlProject
import com.inocybe.poc.model.internal.Item
import com.inocybe.poc.outbound.GitlabConnector

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

object ItemManager {
  case class GetItem(itemId: String)
  case object GetItems
  case class CreateItem(item: Item)
}
class ItemManager extends Controler {

  lazy val gitlabConnector = ActorRegistry.getActor(classOf[GitlabConnector], context)

  override def receive: Receive = {

    case "repository"               =>
      val repo = Await.result(gitlabConnector ? GitlabConnector.GetRepositories, 5 seconds)
        .asInstanceOf[HttpResponse]
        .convertTo[List[GlProject]]
      sender ! ItemsInfo(repo)

    case GetItems =>
      val graph = new NeoGraph()
      val vertices = graph.getVertices(Vertices.Item).map(_.toVertex)
      sender ! ItemsInfo(vertices)

    case GetItem(itemId: String) =>
      try {
        val graph = new NeoGraph()
        val item = graph.getVertex(Vertices.Item, itemId)
        sender ! ItemInfo(item.toVertex)
      } catch {
        case e: java.util.NoSuchElementException =>
          sender ! ServiceException(StatusCodes.NotFound, "Item not found.", s"No Item with id=$itemId")
      }


    case CreateItem(item: Item) =>
      try {
      val graph = new NeoGraph()
      val createdItem = graph.addVertex(Vertices.Item, item)
      sender ! ItemCreated(createdItem.toVertex)
      } catch {
        case e: org.neo4j.driver.v1.exceptions.ClientException =>
          sender ! ServiceException(StatusCodes.Conflict, "Item already exist.", s"Item with id=${item.itemId}")
      }
  }
}
