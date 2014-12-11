package services.feeds.actors

import akka.actor.Actor.Receive
import akka.actor.{ActorLogging, Actor}
import services.messages.Messages.ZoneMessage

/**
 * Created by hashcode on 2014/12/09.
 */
class SocialMediaFeedsActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case ZoneMessage(zone) => {
      GetSocialMediaFeedLinks.processSocialMediaFeeds(zone)
    }
  }
}
