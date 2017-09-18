package sample.blog

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object BotApp {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty)
      startup(Seq("0"))
    else
      startup(args)
  }

  def startup(ports: Seq[String]): Unit = {
    ports foreach { port =>
      // Override the configuration of the port
      val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
        withFallback(ConfigFactory.load())

      // Create an Akka system
      val system = ActorSystem("ClusterSystem", config)

      if (port != "2551" && port != "2552")
        system.actorOf(Props[Bot], "bot")
    }
  }

}

