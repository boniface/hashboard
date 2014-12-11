package domain

import java.util.Date

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/10/17.
 */
case class CustomLink(zone:String,
                      datePublished: Date,
                      linkhash: String,
                      url:String,
                      site: String,
                      siteCode:String,
                      title:String
                       )

object CustomLink {
  implicit val linkFmt = Json.format[CustomLink]
}
