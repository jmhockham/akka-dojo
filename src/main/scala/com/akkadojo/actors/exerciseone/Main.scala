package com.akkadojo.actors.exerciseone

import akka.actor.ActorSystem
import akka.util.Timeout

import scala.concurrent.duration._

object Main extends App {
  implicit val system = ActorSystem("exercise-one")

  implicit val timeout = Timeout(2.seconds)

  println("Press Enter to terminate.")

  val print = system.actorOf(PrintActor.props, PrintActor.name)
  //TODO: Send a Print message to the print actor, sending is done with the '!' operator
  println("This is from main.")

  //val echo = ??? //TODO: Create the EchoActor similar to the print actor
  //TODO: Send an echo message to the echo actor and print the response using the ask pattern

  // val forward = ??? //TODO: Create the forward actor with the print actor as its parameter
  //TODO: Send the forward actor a Print message

  // val printEcho = ??? //TODO: Create the PrintEcho actor
  //TODO send a PrintEcho message to the PrintEcho actor

  System.console.readLine()
  system.shutdown()
}
