package lectures.part3concurrency

object Intro extends App {
  /*
    interface Runnable {
      public void run()
    }
   */
  val runnable = new Runnable {
    override def run(): Unit = println("Running in parallel")
  }

  val aThread = new Thread(runnable)
  aThread.start() // gives the  signal to the JVM to start a JVM thread
  // create a JVM thread => OS thread
  runnable.run() // doesn't do anything in parallel!
  aThread.join() // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("goodbye")))
  threadHello.start()
}
