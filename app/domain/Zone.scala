package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/11/30.
 */
case class Zone(code:String,name:String,status:String, flag:String)

object Zone{
  implicit lazy val zoneFmt = Json.format[Zone]

}
