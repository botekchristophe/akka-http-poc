package com.inocybe.poc.core

import com.typesafe.config.ConfigFactory

trait GlobalConfiguration {

  val config = ConfigFactory.load()

  // GITLAB
  val glApiKey = config.getString("gitlab.apikey")
  val glProjectUrl = config.getString("gitlab.projectUrl")

  // NEO4J

  val neoAddress = config.getString("neo4j.address")
  val neoUser = config.getString("neo4j.user")
  val neoPassword = config.getString("neo4j.password")
}