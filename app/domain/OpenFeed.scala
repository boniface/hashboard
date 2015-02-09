package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/02/05.
 */
case class OpenFeed(  zone: String,
                      siteCode: String,
                      id: String,
                      feedLink: String,
                      feedSite: String,
                      siteLogo: String,
                      filter:String
                       )

object OpenFeed {
  implicit val customFeedFmt = Json.format[OpenFeed]

}