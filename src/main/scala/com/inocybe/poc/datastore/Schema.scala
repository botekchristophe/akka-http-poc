package com.inocybe.poc.datastore

import com.inocybe.poc.datastore.Schema.{E, V}

object Schema {

  sealed trait E {
    def label: String
  }

  sealed trait V {
    def className: String
    def idx: String
    def fields: List[String]
  }
}

object Edges {
  case object HasResetPassword  extends E { def label = "hasItem"}
}

object Vertices {

  case object Item extends V {
    def className = "Item"
    def idx = "itemId"
    def fields = List("itemId", "name", "price")
  }

  case object Order extends V {
    def className = "Order"
    def idx = "orderId"
    def fields = List("orderId", "date")
  }
}