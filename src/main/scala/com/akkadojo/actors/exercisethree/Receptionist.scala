package com.akkadojo.actors.exercisethree

import akka.actor.{ActorRef, Props}
import akka.util.Timeout
import spray.httpx.SprayJsonSupport._
import spray.routing._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


class Receptionist extends HttpServiceActor
                      with ReverseRoute {
  implicit def executionContext = context.dispatcher

  def createChild(props:Props, name:String) = context.actorOf(props, name)
  def receive = runRoute(reverseRoute)

}

trait ReverseRoute extends HttpService {
  implicit def executionContext: ExecutionContext

  def createChild(props:Props, name:String): ActorRef

  val reverseActor = createChild(ReverseActor.props, ReverseActor.name)


  def reverseRoute:Route = path("reverse") {
    post {
      entity(as[ReverseRequest]) { request =>
        implicit val timeout = Timeout(20 seconds)
        import ReverseActor._
        import akka.pattern.ask

        val futureResponse = reverseActor.ask(Reverse(request.value))
                                         .mapTo[ReverseResult]
                                         .map(r=> ReverseResponse(r.value))

        complete(futureResponse)
      }
    }
  }
}

