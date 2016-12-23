package com.inocybe.poc.api

import akka.http.scaladsl.server
import akka.http.scaladsl.server.Directives._
import com.inocybe.poc.api.Service.{ItemCreated, ItemInfo}
import com.inocybe.poc.model.ModelObject
import com.inocybe.poc.model.JsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json._

import scala.util.{Success, Try}

object Service {
  case class ItemCreated(item: ModelObject)
  case class ItemInfo(item: ModelObject)
}


trait Service {

  val futureHandler: PartialFunction[Try[Any], server.Route] = {
    case Success(response: ItemCreated) =>
      complete(201, response.item)
    case Success(response: ItemInfo) =>
      complete(200, response.item)
    case unknown: Any =>
      complete(500, unknown.toString)
  }
}
