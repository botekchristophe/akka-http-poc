package com.inocybe.poc.model

import spray.json.DefaultJsonProtocol._
import spray.json.{DeserializationException, JsValue, RootJsonFormat}
import spray.json._

object JsonProtocol {
  implicit val itemFormat = jsonFormat2(Item)

  implicit object ModelObjectJsonFormat extends RootJsonFormat[ModelObject] {
    def write(obj: ModelObject) = obj match {
      case o: Item  => o.toJson
      case _ => throw DeserializationException("This ModelObject does not have a json format")
    }
    def read(value: JsValue) = throw DeserializationException("Cannot deserialize to ModelObject")
  }
}
