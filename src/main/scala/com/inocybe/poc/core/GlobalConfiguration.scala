package com.inocybe.poc.core

import com.typesafe.config.ConfigFactory

trait GlobalConfiguration {

  val config = ConfigFactory.load()

  // GITLAB
  val glApiKey = config.getString("gitlab.apikey")
  val glProjectUrl = config.getString("gitlab.projectUrl")
}