package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/02/06.
 */
case class Social(
                   service: String,
                   props: Map[String, String]
                   )

object Social {
  implicit val socialFmt = Json.format[Social]
}
