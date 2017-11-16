package com.akkadojo.actors.pancakes

import akka.{Done, NotUsed}
import akka.stream.{ActorMaterializer, FlowShape}
import akka.stream.scaladsl.{Balance, Flow, GraphDSL, Merge, Sink, Source}
import GraphDSL.Implicits._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._

import scala.concurrent.Future

object ParallelDemo extends App {


  val fryingPan1: Flow[ScoopOfBatter, HalfCookedPancake, NotUsed] =
    Flow[ScoopOfBatter].map { batter => HalfCookedPancake(batter.ingredients) }

  // Finishes a half-cooked pancake
  val fryingPan2: Flow[HalfCookedPancake, Pancake, NotUsed] =
    Flow[HalfCookedPancake].map { halfCooked => Pancake(halfCooked.ingredients)  }

  // With the two frying pans we can fully cook pancakes
  val pancakeChef: Flow[ScoopOfBatter, Pancake, NotUsed] =
    Flow[ScoopOfBatter].via(fryingPan1.async).via(fryingPan2.async)


  val pancakeChefs1: Flow[ScoopOfBatter, HalfCookedPancake, NotUsed] =
    Flow.fromGraph(GraphDSL.create() { implicit builder =>
      val dispatchBatter = builder.add(Balance[ScoopOfBatter](2))
      val mergeHalfPancakes = builder.add(Merge[HalfCookedPancake](2))

      // Two chefs work with one frying pan for each, half-frying the pancakes then putting
      // them into a common pool
      dispatchBatter.out(0) ~> fryingPan1.async ~> mergeHalfPancakes.in(0)
      dispatchBatter.out(1) ~> fryingPan1.async ~> mergeHalfPancakes.in(1)

      FlowShape(dispatchBatter.in, mergeHalfPancakes.out)
    })

  val pancakeChefs2: Flow[HalfCookedPancake, Pancake, NotUsed] =
    Flow.fromGraph(GraphDSL.create() { implicit builder =>
      val dispatchHalfPancakes = builder.add(Balance[HalfCookedPancake](2))
      val mergePancakes = builder.add(Merge[Pancake](2))

      // Two chefs work with one frying pan for each, finishing the pancakes then putting
      // them into a common pool
      dispatchHalfPancakes.out(0) ~> fryingPan2.async ~> mergePancakes.in(0)
      dispatchHalfPancakes.out(1) ~> fryingPan2.async ~> mergePancakes.in(1)

      FlowShape(dispatchHalfPancakes.in, mergePancakes.out)
    })

  val kitchen: Flow[ScoopOfBatter, Pancake, NotUsed] = pancakeChefs1.via(pancakeChefs2)

  implicit val system = ActorSystem("pancake-system")
  implicit val materializer = ActorMaterializer()

  kitchen.runWith(Source(List(ScoopOfBatter("butter"), ScoopOfBatter("kryptonite"))),sink)


  def sink(): Sink[Pancake, Future[Done]] = {
    Sink.foreach { (pancake: Pancake) =>
      println("Made a pancake with: " + pancake.ingredients)
    }
  }

}

/***** Case Classes *****/

case class ScoopOfBatter(ingredients: String = "")

case class HalfCookedPancake(ingredients: String = "")

case class Pancake(ingredients: String = "")
