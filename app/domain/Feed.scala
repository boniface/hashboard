package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class Feed(zone: String,
                id: String,
                feedLink: String,
                feedType: String,
                feedSite: String,
                siteLogo: String,
                siteCode: String
                 )

object Feed {
  implicit val feedFmt = Json.format[Feed]

}
