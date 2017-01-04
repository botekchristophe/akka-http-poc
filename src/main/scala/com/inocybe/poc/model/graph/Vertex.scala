package com.inocybe.poc.model.graph

import com.inocybe.poc.model.ModelObject

case class Vertex(label: String, uuid: Option[String] = None, props: Map[String, String]) extends ModelObject