package com.akkadojo.excercisethree

import akka.actor.{Props, ActorSystem}
import akka.io.IO

import spray.can.Http
import spray.can.Http.Bind

/**
  * Created by jmhockham on 05/09/17.
  */
object Main extends App {

  implicit val system = ActorSystem("exercise-three")

  val receptionist = system.actorOf(Props(new Receptionist), "receptionist")

  IO(Http) ! Bind(listener= receptionist, interface = "0.0.0.0", port=8000)
}
