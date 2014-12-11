package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class Stats (
                   id: String,
                   item:String,
                   counter: Long
                   )

object Stats {
  implicit lazy val statsFmt = Json.format[Stats]
}