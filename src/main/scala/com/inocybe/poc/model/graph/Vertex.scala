package com.inocybe.poc.model.graph

import com.inocybe.poc.model.ModelObject

case class Vertex(label: String, uid: Option[String] = None, props: Map[String, String]) extends ModelObject