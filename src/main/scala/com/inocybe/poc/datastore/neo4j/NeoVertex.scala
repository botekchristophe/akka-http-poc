package com.inocybe.poc.datastore.neo4j

import java.lang.Iterable
import java.util

import com.inocybe.poc.model.graph.Vertex
import com.tinkerpop.blueprints.{Direction, Edge, VertexQuery, Vertex => BPVertex}


class NeoVertex(label: String, uid: String, props: Map[String, String]) extends BPVertex {
  override def addEdge(label: String, inVertex: BPVertex): Edge = ???

  override def getEdges(direction: Direction, labels: String*): Iterable[Edge] = ???

  override def query(): VertexQuery = ???

  override def getVertices(direction: Direction, labels: String*): Iterable[BPVertex] = ???

  override def getProperty[T](key: String): T = ???

  override def getId: String = this.uid

  override def setProperty(key: String, value: scala.Any): Unit = ???

  override def getPropertyKeys: util.Set[String] = ???

  override def remove(): Unit = {
    val graph = new NeoGraph()
    graph.removeVertex(this)
    graph.shutdown()
  }

  override def removeProperty[T](key: String): T = ???

  def getLabel = this.label
  def getUuid = this.uid
  def getProps = this.props

  def toVertex: Vertex = {
    Vertex(this.label, Option(this.uid), this.props)
  }
}
