package com.inocybe.poc.core

import akka.actor.{ActorRef, ActorRefFactory, ActorSystem, Props}
import akka.util.Timeout
import com.inocybe.poc.controler.ItemManager
import com.inocybe.poc.outbound.GitlabConnector

import scala.concurrent.Await
import scala.language.postfixOps
import scala.concurrent.duration._

object ActorRegistry {

  implicit val timeout = Timeout(15.seconds)
  val actorList = Set[Class[_]] (
    classOf[GitlabConnector],
    classOf[ItemManager]
  )

  /**
    * initialize database schema and wso2
    *
    * @param system
    *               akka actor system
    */
  def init(system: ActorSystem)(implicit timeout: Timeout): Unit = {
    actorList.foreach(ref => {
      system.actorOf(Props(ref), ref.getName)
    })
  }

  /**
    * select any actor of the micro service based on its type.
    * If this actor cannot be selected, it instantiate a new one with no default name.
    *
    * @param ref
    *            classOf[Actor]
    * @param context
    *               actor ref factory
    * @return
    *         an actorRef on selected actor
    */
  def getActor(ref: Class[_], context: ActorRefFactory): ActorRef = {
    Await.result(context.actorSelection(s"/user/${ref.getName}").resolveOne(), 3 seconds)
  }
}
