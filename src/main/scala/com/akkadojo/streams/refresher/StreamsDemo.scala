package com.akkadojo.streams.refresher

import akka.Done
import akka.actor.ActorSystem
import akka.stream.scaladsl.{ Flow, Keep, Sink, Source }
import akka.stream.{ ActorMaterializer, Materializer }
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration.Duration

/**
  Basic refresher on streams running sequentially
 */

object StreamsDemo {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("demo-akka-streams-system")
    implicit val mat = ActorMaterializer()
    import system.dispatcher

    // Sequencing the demos which would run in parallel otherwise!
    for {
      _ <- demo1()
      d2 <- demo2()
      d3 <- demo3()
    } {
      println(s"Demo 2: $d2")
      println(s"Demo 3: $d3")
      Await.ready(system.terminate(), Duration.Inf)
    }
  }

  def demo1()(implicit mat: Materializer): Future[Done] = {
    //val printNumbers = Source(1.to(7)).to(Sink.foreach(println))
    val printNumbers = Source(1.to(7)).toMat(Sink.foreach(println))(Keep.right)
    println("Demo 1:")
    printNumbers.run()
  }

  def demo2()(implicit mat: Materializer): Future[Int] =
    Source(1.to(7)).runWith(Sink.fold(0)(_ + _))

  def demo3()(implicit mat: Materializer): Future[Int] = {
    val doubler = Flow[Int].map(_ * 2)
    Source.fromIterator(() => Iterator.from(1))
      .via(doubler)
      .take(7)
      .map(_ + 1)
      .filter(_ % 3 == 0)
      .runWith(Sink.fold(0)(_ + _))
  }
}
