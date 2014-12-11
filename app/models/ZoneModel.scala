package models

import java.util.UUID

import conf.Util
import domain.Zone
import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class ZoneModel( name: String,
                      code: String,
                      flag: String) {
  def getDomain(): Zone = ZoneModel.domain(this)
}

object ZoneModel {
  implicit val zoneFmt = Json.format[ZoneModel]
  def domain(model: ZoneModel) = {
    Zone(model.code, model.name, Util.ENABLED.toString, model.flag)
  }
}