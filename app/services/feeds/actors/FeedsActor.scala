package services.feeds.actors

import akka.actor.{Actor, ActorLogging}
import services.messages.Messages.ZoneMessage

/**
 * Created by hashcode on 2014/12/09.
 */
class FeedsActor extends Actor with ActorLogging {
  override def receive: Receive = {

    case ZoneMessage(zone) => {
      GetFeedLinks.processFeed(zone)
    }
  }
}
