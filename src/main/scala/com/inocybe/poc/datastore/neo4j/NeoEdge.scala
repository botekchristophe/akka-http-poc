package com.inocybe.poc.datastore.neo4j

import java.util

import com.tinkerpop.blueprints.{Direction, Edge => BPEdge, Vertex}
import com.inocybe.poc.model.graph.Edge

class NeoEdge(label: String, uid: String) extends BPEdge {
  override def getLabel: String = this.label

  override def getVertex(direction: Direction): Vertex = ???

  override def getProperty[T](key: String): T = ???

  override def getId: String = this.uid

  override def setProperty(key: String, value: scala.Any): Unit = ???

  override def getPropertyKeys: util.Set[String] = ???

  override def remove(): Unit = {
    val graph = new NeoGraph()
    graph.removeEdge(this)
    graph.shutdown()
  }

  override def removeProperty[T](key: String): T = ???

  def toEdge: Edge = {
    Edge(this.label, Option(this.uid))
  }
}
