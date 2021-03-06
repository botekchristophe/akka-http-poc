package com.inocybe.poc.model

trait ModelObject {
  /**
    * Convert a case class to a map from field name to field value
    * For Options: if the case class has a field Some(x), it will be represented as x, and
    * a field None will not be represented at all
    */
  def toMap =
  (Map[String, AnyRef]() /: this.getClass.getDeclaredFields) { (a, f) =>
    f.setAccessible(true)
    f.get(this) match {
      case Some(x: AnyRef) => a + (f.getName -> x)
      case None => a
      case other => a + (f.getName -> other)
    }
  }

  def asCypherProps: String = {
    this.toMap.map {
      case (key, value) => s"$key: \'${value.toString}\'"
    }.toList.mkString("{", ", ", "}")
  }

  def asCypherObject(name: String): String = {
    s"($name: ${this.getClass.toString.split(".").last} $asCypherProps)"
  }
}
