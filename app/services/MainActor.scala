package services

import akka.actor.{Actor, Props}
import akka.event.Logging
import akka.routing.RoundRobinPool
import services.feeds.actors.{CustomFeedsActor, FeedsActor, SocialMediaFeedsActor}
import services.messages.Messages.{StartMessage, ZoneMessage}
import services.posts.actors.{PostCustonFeedLinkContentActor, PostFeedLinkContentActor}

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/09.
 */
class MainActor extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case StartMessage(start) => {
      val feedsActor = context.actorOf(Props[FeedsActor].withRouter(RoundRobinPool(nrOfInstances = 5)))
      val customFeedsActor = context.actorOf(Props[CustomFeedsActor].withRouter(RoundRobinPool(nrOfInstances = 5)))
      val socialMediaFeedsActor = context.actorOf(Props[SocialMediaFeedsActor].withRouter(RoundRobinPool(nrOfInstances = 5)))
      val postFeedLinkContentActor = context.actorOf(Props[PostFeedLinkContentActor].withRouter(RoundRobinPool(nrOfInstances = 5)))
      val postCustonFeedLinkContentActor = context.actorOf(Props[PostCustonFeedLinkContentActor].withRouter(RoundRobinPool(nrOfInstances = 5)))

      ZoneService.getZones map (zones => zones foreach (
        zone => {
          socialMediaFeedsActor ! ZoneMessage(zone.code)
          customFeedsActor ! ZoneMessage(zone.code)
          feedsActor ! ZoneMessage(zone.code)
          postFeedLinkContentActor ! ZoneMessage(zone.code)
          postCustonFeedLinkContentActor ! ZoneMessage(zone.code)
        })
        )
    }
    case _ => log.info("received unknown message")
  }

}
