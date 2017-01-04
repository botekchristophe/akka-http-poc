package com.inocybe.poc.model.graph

import com.inocybe.poc.datastore.neo4j.NeoEdge
import com.inocybe.poc.model.ModelObject

case class Edge(label: String, uuid: Option[String] = None) extends ModelObject
