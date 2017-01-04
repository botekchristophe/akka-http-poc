package com.inocybe.poc.model.graph

import com.inocybe.poc.model.ModelObject

case class Edge(label: String, uid: Option[String] = None) extends ModelObject
