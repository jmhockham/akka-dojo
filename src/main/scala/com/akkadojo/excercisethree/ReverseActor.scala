package com.akkadojo.excercisethree

import akka.actor.{Actor, Props}

/**
  * Created by jmhockham on 05/09/17.
  */
object ReverseActor {
  def props = Props(new ReverseActor)
  def name = "reverser"

  case class Reverse(value:String)
  case class ReverseResult(value:String)
  //TODO add a PalindromeResult
}

class ReverseActor extends Actor {
  import ReverseActor._

  def receive = {
    case Reverse(value) =>
      //TODO verify if the value is a palindrome, return a PalindromeResult if this is the case,
      sender ! ReverseResult(value.reverse)
  }
}
