package services.posts.actors

import akka.actor.{Actor, ActorLogging}
import services.PublishService
import services.messages.Messages.ZoneMessage
import services.posts.{FetchContentFromRssLinks, LinksService}

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/10.
 */
class PostFeedLinkContentActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case ZoneMessage(zone) => {
      LinksService.getLatestLinks(zone) map (links => {
        if (links.size > 0) {
          links foreach (link => {
            val published = PublishService.isPublished("POST", link.linkhash)
            published map (post => post match {
              case Some(post) => None
              case None => FetchContentFromRssLinks.getContent(link)
            })
          }
            )
        }
      })
    }
  }
}
