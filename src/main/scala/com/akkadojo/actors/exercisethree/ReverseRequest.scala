package com.akkadojo.actors.exercisethree

import spray.json.DefaultJsonProtocol

/**
  * Created by jmhockham on 05/09/17.
  */
case class ReverseRequest(value:String)

object ReverseRequest extends DefaultJsonProtocol {
  implicit val format = jsonFormat1(ReverseRequest.apply)
}

case class ReverseResponse(value:String, isPalindrome:Boolean = false)

object ReverseResponse extends DefaultJsonProtocol {
  implicit val format = jsonFormat2(ReverseResponse.apply)
}