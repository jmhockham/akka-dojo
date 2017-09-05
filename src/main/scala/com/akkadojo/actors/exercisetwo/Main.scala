package com.akkadojo.actors.exercisetwo

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.duration._


object Main {
  implicit val system = ActorSystem("exercise-two")

  implicit val timeout = Timeout(2.seconds)

  //TODO implement the receptionist
  //val receptionist = system.actorOf(Props(new Receptionist), "receptionist")

  //IO(Http) ! Bind(listener= receptionist, interface = "0.0.0.0", port=8000)
  system.shutdown()
}
