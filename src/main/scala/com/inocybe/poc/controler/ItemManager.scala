package com.inocybe.poc.controler

import akka.actor.Actor
import com.inocybe.poc.api.Service.{ItemCreated, ItemInfo}
import com.inocybe.poc.model.Item

class ItemManager extends Actor {

  val items = scala.collection.mutable.HashMap.empty[Long, String]

  override def receive: Receive = {
    case id: Long                   =>
      sender ! ItemInfo(Item(id, items(id)))

    case i: Item =>
      items.put(i.id, i.name)
      sender ! ItemCreated(i)
  }
}
