package com.akkadojo.excercisethree

import akka.actor.{ActorContext, ActorRef, Props}

/**
  * Created by jmhockham on 04/09/17.
  */
trait CreationSupport {
  def getChild(name:String):Option[ActorRef]
  def createChild(props:Props, name:String):ActorRef
  def getOrCreateChild(props:Props, name:String):ActorRef = getChild(name).getOrElse(createChild(props, name))
}

trait ActorContextCreationSupport extends CreationSupport {
  def context:ActorContext

  def getChild(name:String):Option[ActorRef] = context.child(name)
  def createChild(props:Props, name:String):ActorRef = context.actorOf(props, name)
}