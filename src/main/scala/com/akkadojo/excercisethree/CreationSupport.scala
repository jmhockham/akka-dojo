package com.akkadojo.excercisethree

/**
  * Created by jmhockham on 05/09/17.
  */
import akka.actor.{ActorRef, Props}

trait CreationSupport {

  def getChild(name:String):Option[ActorRef]
  def createChild(props:Props, name:String):ActorRef
  def getOrCreateChild(props:Props, name:String):ActorRef = getChild(name).getOrElse(createChild(props, name))
}

// TODO create an ActorContextCreationSupport trait that extends the CreationSupport and uses an ActorContext to implement the three methods

