package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/02/06.
 */
case class PublishedLinks( item: String,
                      linkhash:String
                      )

object PublishedLinks {
  implicit val pubsFmt = Json.format[PublishedLinks]
}