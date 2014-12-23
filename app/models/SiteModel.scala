package models

import domain.Site
import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class SiteModel(zone: String,
                     url: String,
                     code: String,
                     logo: String
                      ) {
  def getDomain(): Site = SiteModel.domain(this)

}

object SiteModel {
  implicit val zoneFmt = Json.format[SiteModel]

  def domain(model: SiteModel) = {
    Site(model.zone,
      model.url,
      model.code,
      model.logo)
  }
}


