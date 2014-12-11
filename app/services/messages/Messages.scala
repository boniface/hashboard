package services.messages

import com.rometools.rome.feed.synd.SyndEntry
import domain._

/**
 * Created by hashcode on 2014/09/27.
 */
object Messages {
  case class StartMessage(message:String)
  case class ZoneMessage(zone:String)
  case class LinksMessage(links:Seq[Link])
  case class CustomFeedsMessage(feeds:Seq[CustomFeed])
  case class PostSocialMediaContent(links: List[SyndEntry], feed: SocialMediaFeed)

}
