package com.inocybe.poc.datastore.neo4j

import com.inocybe.poc.core.GlobalConfiguration
import com.inocybe.poc.datastore.Schema.V
import com.inocybe.poc.model.ModelObject
import org.neo4j.driver.v1.{AuthTokens, GraphDatabase, Record}

import scala.collection.JavaConversions._

class NeoGraph extends GlobalConfiguration {

  val driver = GraphDatabase.driver(neoAddress, AuthTokens.basic(neoUser, neoPassword))
  val session = driver.session()

  def shutdown(): Unit = {
    session.close()
    driver.close()
  }

  def getEdge(id: String): NeoEdge = {
    session.run(s"MATCH ()-[e]->() WHERE id(e)=${id.toInt} RETURN type(e) as label, id(e) as uuid")
      .toList
      .map(toEdge(_))
      .head
  }

  def addEdge(outV: NeoVertex, edgeName: String, inV: NeoVertex): NeoEdge = {
    session.run(
      s"""
         |MATCH (outV), (inV)
         |WHERE id(outV)=${outV.getUuid.toInt} and id(inV)=${inV.getUuid.toInt}
         |CREATE (outV)-[e:$edgeName]->(inV)
         |return type(e) as label, e.uuid as uuid
       """.stripMargin)
      .toList
      .map(toEdge(_))
      .head
  }

  def getEdges: List[NeoEdge] = {
    session.run(s"MATCH ()-[e]->() RETURN type(e) as label, id(e) as uuid")
      .toList
      .map(toEdge(_))
  }

  def getEdges(label: String): List[NeoEdge] = {
    session.run(s"MATCH ()-[e:$label]->() RETURN type(e) as label, id(e) as uuid")
      .toList
      .map(toEdge(_))
  }

  def removeEdge(edge: NeoEdge): Unit = {
    session.run(
      s"""
         |MATCH ()-[e]->()
         |WHERE id(e)=${edge.getId.toInt}
         |DELETE e
       """.stripMargin)
  }

  def addVertex(v: V, obj: ModelObject): NeoVertex = {
    session.run(
      s"""
         |CREATE (v:${v.className}${obj.asCypherProps})
         |RETURN labels(v) as label, id(v) as uuid, v
       """.stripMargin)
      .toList
      .map(toVertex(_))
      .head
  }

  def getVertex(v: V, idxValue: String): NeoVertex = {
    session.run(s"MATCH (v:${v.className}) WHERE v.${v.idx}='$idxValue' RETURN labels(v) as label, id(v) as uuid, v")
      .toList
      .map(toVertex(_))
      .head
  }

  def getVertex(id: String): NeoVertex = {
    session.run(s"MATCH (v) WHERE id(v)=${id.toInt} RETURN labels(v) as label, id(v) as uuid, v")
      .toList
      .map(toVertex(_))
      .head
  }

  def getVertices: List[NeoVertex] = {
    session.run(s"MATCH (v) RETURN labels(v) as label, id(v) as uuid, v")
      .toList
      .map(toVertex(_))
  }

  def getVertices(label: String): List[NeoVertex] = {
    session.run(s"MATCH (v:$label) RETURN labels(v) as label, id(v) as uuid, v")
      .toList
      .map(toVertex(_))
  }

  def getVertices(v: V): List[NeoVertex] = {
    session.run(s"MATCH (v:${v.className}) RETURN labels(v) as label, id(v) as uuid, v")
      .toList
      .map(toVertex(_))
  }

  def removeVertex(vertex: NeoVertex): Unit = {
    session.run(
      s"""
         |MATCH (v)
         |WHERE id(v)=${vertex.getId.toInt}
         |DELETE e
       """.stripMargin)
  }

  private def toVertex(record: Record): NeoVertex = {
    new NeoVertex(
      label = record.get("label").asList().head.toString,
      uid = record.get("uuid").asInt().toString,
      props = record.get("v").asMap().toMap.map{ case (k,v) => (k, v.toString)})
  }

  private def toEdge(record: Record): NeoEdge = {
    new NeoEdge(label = record.get("label").asString(), record.get("uuid").asInt().toString)
  }
}
