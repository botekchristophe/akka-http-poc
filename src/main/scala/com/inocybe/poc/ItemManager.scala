package com.inocybe.poc

import akka.actor.Actor

class ItemManager extends Actor {

  val items = scala.collection.mutable.HashMap.empty[Long, String]

  override def receive: Receive = {
    case id: Long                   =>
      sender ! Item(id, items(id))

    case i: Item =>
      items.put(i.id, i.name)
      sender ! s"Added ${i.id} -> ${i.name} to items"
  }
}
