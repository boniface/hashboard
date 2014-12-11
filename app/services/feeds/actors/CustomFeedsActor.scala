package services.feeds.actors

import akka.actor.Actor.Receive
import akka.actor.{ActorLogging, Actor}
import respository.CustomFeedRepository
import services.messages.Messages.ZoneMessage
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/09.
 */
class CustomFeedsActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case ZoneMessage(zone) => {

      GetCustomFeedLinks.processCustomFeeds(zone)

    }
  }
}
