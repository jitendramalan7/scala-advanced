package lectures.part1as

import scala.util.Try

object DarkSugars extends App {
  // syntax sugar #1: methods with single param
  def singleArgMethod(arg: Int): String = s"$arg little ducks..."
//  println(singleArgMethod(7))

  val description = singleArgMethod {
    // write some complex code
    42
  }
  println(description)

  val aTryInstance = Try {  // java's try {...}
    throw new RuntimeException
  }

  val list = List(1, 2, 3).map { x =>
    x + 1
  }
//  print(list)

  // syntax sugar #2: single abstract method
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = x + 1
  }

  val afunkyInstance: Action = (x: Int) => x + 1 // magic

  // example: Runnables
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Hello, Scala")
  })

  val aSweeterThread = new Thread(() => println("Sweet, Scala!"))
//  println(aSweeterThread.run)

  abstract class AnAbstractType {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (a: Int) => println(s"Sweet $a...")
  println(anAbstractInstance.f(7))

  // syntax sugar #3: the :: and #:: methods are special
  val prependedList = 2 :: List(3, 4)
//  2.::(List(3,4)) // compilation error
  List(3,4).::(2) // prepend 2 in the list(List(2, 3, 4)
  // ?!
//  println(prependedList)

  // scala spec: last char decides associativity of method
  val anotherPrependedList = 1 :: 2 :: 3 :: List(4, 5)
  List(4,5).::(3).::(2).::(1) // equivalent
//  println(anotherPrependedList)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = {
      println(value) // actual implementation here
      this
    }
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]
//  myStream.-->:(3)

  // syntax sugar #4: multi-word method naming
  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet!"

  // syntax sugar #5: infix types
  class Composite[A, B]
  val composite: Int Composite String = ??? // same as val composite: Composite[Int, String] = ???

  class -->[A, B]
  val towards: Int --> String = ???

  // syntax sugar #6: update() is very special, much like apply()
  val anArray = Array(1,2,3)
  anArray(2) = 7  // rewritten to anArray.update(2, 7)
  // used in mutable collections
  // remember apply() AND update()!

  // syntax sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation
    def member = internalMember // "getter"
    def member_=(value: Int): Unit =
      internalMember = value // "setter"
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // rewrittern as aMutableContainer.member_=(42)
}
