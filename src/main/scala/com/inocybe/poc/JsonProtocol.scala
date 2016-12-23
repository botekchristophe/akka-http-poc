package com.inocybe.poc

import spray.json.DefaultJsonProtocol._

object JsonProtocol {
  implicit val itemFormat = jsonFormat2(Item)
}
