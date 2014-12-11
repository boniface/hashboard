package models

import java.util.UUID

import conf.Util
import domain.Site
import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class SiteModel(zone: String,
                     name: String,
                     url: String,
                     description: String,
                     logo:String

                      ) {
  def getDomain(): Site = SiteModel.domain(this)

}

object SiteModel {
  implicit val zoneFmt = Json.format[SiteModel]

  def domain(model: SiteModel) = {
    Site(Util.md5Hash(UUID.randomUUID().toString()),
      model.zone,
      model.name,
      model.url,
      model.description,
      model.logo)
  }
}


