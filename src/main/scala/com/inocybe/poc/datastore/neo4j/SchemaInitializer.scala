package com.inocybe.poc.datastore.neo4j

import com.inocybe.poc.datastore.Schema.V
import com.inocybe.poc.datastore.Vertices

object SchemaInitializer {
  val graph = new NeoGraph()
  def init(): Unit = {
    initClass(Vertices.Item)
    initClass(Vertices.Order)
  }

  private def initClass(v: V): Unit = {
    graph.session.run(s"DROP INDEX ON :${v.className}(${v.idx})")
    graph.session.run(s"CREATE CONSTRAINT ON (v:${v.className}) ASSERT v.${v.idx} IS UNIQUE")
  }
}
