package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class Site( zone:String,
                 url: String,
                 code:String,
                 logo:String
                 )
object Site {
  implicit val siteFmt = Json.format[Site]
}
