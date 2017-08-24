package com.akkadojo.excercisetwo

import scala.concurrent.duration._
import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.akkadojo.exercisetwo.Receptionist


object Main {
  implicit val system = ActorSystem("exercise-two")
  import system.dispatcher

  implicit val timeout = Timeout(2.seconds)

  val receptionist = system.actorOf(Props[Receptionist], "receptionist")

  println("Press Enter to terminate.")

  //TODO add in reverse function

  System.console.readLine()
  system.shutdown()
}
