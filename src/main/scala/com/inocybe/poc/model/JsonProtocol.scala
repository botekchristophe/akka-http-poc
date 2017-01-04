package com.inocybe.poc.model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.inocybe.poc.model.exception.ErrorDetail
import com.inocybe.poc.model.gitlab.GlProject
import com.inocybe.poc.model.graph.{Edge, Vertex}
import com.inocybe.poc.model.internal.Item
import spray.json.{DeserializationException, JsValue, RootJsonFormat, _}

object JsonProtocol extends DefaultJsonProtocol  with SprayJsonSupport {
  implicit val itemFormat = jsonFormat2(Item)
  implicit val errorFormat = jsonFormat4(ErrorDetail)
  implicit val glProjectFormat = jsonFormat2(GlProject)

  implicit val edgeFormat = jsonFormat2(Edge)
  implicit val vertexFormat = jsonFormat3(Vertex)

  implicit object ModelObjectJsonFormat extends RootJsonFormat[ModelObject] {
    def write(obj: ModelObject) = obj match {
      case o: Item        => o.toJson
      case o: ErrorDetail => o.toJson
      case o: GlProject   => o.toJson
      case o: Edge        => o.toJson
      case o: Vertex      => o.toJson
      case _ => throw DeserializationException("This ModelObject does not have a json format")
    }
    def read(value: JsValue) = throw DeserializationException("Cannot deserialize to ModelObject")
  }
}
