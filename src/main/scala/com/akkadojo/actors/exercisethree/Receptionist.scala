package com.akkadojo.actors.exercisethree

import akka.util.Timeout
import spray.routing.{HttpService, HttpServiceActor, Route}

import scala.concurrent.ExecutionContext

/**
  * Created by jmhockham on 05/09/17.
  */
//TODO mixin your ActorContextCreationSupport trait
/*
class Receptionist extends HttpServiceActor
  with ReverseRoute {
  implicit def executionContext = context.dispatcher

  def receive = runRoute(reverseRoute)
}
*/
//TODO mixin the CreationSupport trait so createChild will be available here
trait ReverseRoute extends HttpService {
  /*
  implicit def executionContext: ExecutionContext

  import ReverseActor._

  private val reverseActor = createChild(props, name)

  def reverseRoute:Route = path("reverse") {
    post {
      entity(as[ReverseRequest]) { request =>
        implicit val timeout = Timeout(20 seconds)

        //TODO based on the result (ReverseResult or PalindromeResult)
        // complete with a ReverseResponse that indicates isPalindrome
        val futureResponse = reverseActor.ask(Reverse(request.value))
          .mapTo[ReverseResult].map(r=>ReverseResponse(r.value))

        complete(futureResponse)
      }
    }
  }
  */
}
