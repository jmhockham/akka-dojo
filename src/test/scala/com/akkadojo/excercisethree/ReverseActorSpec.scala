package com.akkadojo.excercisethree

import com.akkadojo.TestSupport.AkkaTestkitContext
import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest

/**
  * Created by jmhockham on 04/09/17.
  */
class ReverseActorSpec extends Specification
  with Specs2RouteTest {

  "The ReverseActor" should {
    "Reverse a string that it receives if it is not a Palindrome" in new AkkaTestkitContext() {
      import ReverseActor._

      val reverseActor = system.actorOf(props, name)

      reverseActor ! Reverse("reverse this!")

      expectMsg(ReverseResult("!siht esrever"))

      expectNoMsg()

    }

    "Not reverse a string but return a PalindromeResult if the reversal has no effect" in new AkkaTestkitContext() {
      import ReverseActor._

      val reverseActor = system.actorOf(props, name)

      reverseActor ! Reverse("akka")

      expectMsg(PalindromeResult)

      expectNoMsg()

    }

  }
}
