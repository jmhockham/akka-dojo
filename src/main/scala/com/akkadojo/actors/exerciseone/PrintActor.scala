package com.akkadojo.actors.exerciseone

import akka.actor.Props

object PrintActor {
  //define messages for print actor here (Print)
  case class Print(text: String)
  //define props and name for PrintActor here
  //val props = Props(new PrintActor)
  val name = "print-actor"
}

class PrintActor { // TODO extend from Actor

  def receive = {
    //TODO handle the Print message here by println'ing the text in the Print message
    // case ... =>
  }
}
