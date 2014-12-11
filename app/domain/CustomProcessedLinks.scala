package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/10/25.
 */
case class CustomProcessedLinks(  linkhash: String )

object CustomProcessedLinks {
  implicit val cpLinksFmt = Json.format[CustomProcessedLinks]
}