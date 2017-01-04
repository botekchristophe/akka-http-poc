package com.inocybe.poc.model.gitlab

import com.inocybe.poc.model.ModelObject

case class GlProject(id: Int,
                     description: Option[String] = None) extends ModelObject
