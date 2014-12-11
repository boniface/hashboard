package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class Site(
                 id: String,
                 zone:String,
                 name: String,
                 url: String,
                 description: String,
                 logo:String
                 )
object Site {
  implicit val siteFmt = Json.format[Site]
}
