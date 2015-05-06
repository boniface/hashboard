package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/05/01.
 */
case class HashTag(
                    zone: String,
                    description: String,
                    hashtag: String
                    )

object HashTag {
  implicit val hashtagFmt = Json.format[HashTag]
}