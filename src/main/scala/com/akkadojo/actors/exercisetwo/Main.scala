package com.akkadojo.actors.exercisetwo

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.duration._


object Main {
  implicit val system = ActorSystem("exercise-two")

  implicit val timeout = Timeout(2.seconds)

  val receptionist = system.actorOf(Props[Receptionist], "receptionist")

  println("Press Enter to terminate.")

  //TODO add in reverse function

  System.console.readLine()
  system.shutdown()
}
