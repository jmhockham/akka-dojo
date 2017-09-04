package com.akkadojo.excercisethree

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import spray.can.Http.Bind

/**
  can be called with:
  curl -d '{"value":"string to reverse"}' -H "Content-Type: application/json" -X POST http://localhost:8000/reverse
  */

object Main extends App {

  implicit val system = ActorSystem("exercise-two")

  val receptionist = system.actorOf(Props[Receptionist], "receptionist")

  IO(Http) ! Bind(listener= receptionist, interface = "0.0.0.0", port=8000)
}