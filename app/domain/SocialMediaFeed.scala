package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class SocialMediaFeed(zone:String,
                           id: String,
                           feedLink: String,
                           feedType: String,
                           feedSite: String,
                           siteLogo: String,
                           siteCode:String
                            )


object SocialMediaFeed {
  implicit val smfeedFmt = Json.format[SocialMediaFeed]

}