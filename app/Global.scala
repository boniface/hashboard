import java.util.concurrent.TimeUnit

import akka.actor.Props
import conf.CORSFilter
import play.api.libs.concurrent.Akka
import play.api.mvc.WithFilters
import play.api.{Application, GlobalSettings, Logger}
import services.MainActor
import services.messages.Messages.StartMessage

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

/**
 * Created by hashcode on 2014/12/01.
 */
object Global extends WithFilters(CORSFilter()) with GlobalSettings {
  override def onStart(app: Application): Unit = {

    super.onStart(app)
    schedular(app)
  }

  def schedular(app: Application) = {
    Logger.info("Starting The Daemon")
    val mainActor = Akka.system(app).actorOf(Props(new MainActor()))
    Akka.system(app).scheduler.schedule(
      Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay 0 milliseconds
      Duration.create(15, TimeUnit.MINUTES), //Frequency 15 minutes
      mainActor, StartMessage("START")
    )
  }

}
