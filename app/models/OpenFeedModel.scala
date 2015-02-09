package models

import java.util.UUID

import conf.Util
import domain.{OpenFeed, CustomFeed}
import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/02/05.
 */
case class OpenFeedModel(zone: String,
                    siteCode: String,
                    feedLink: String,
                    feedSite: String,
                    siteLogo: String,
                    filter:String
                     ){
  def getDomain(): OpenFeed = OpenFeedModel.domain(this)
}

object OpenFeedModel {
  implicit val customFeedFmt = Json.format[OpenFeedModel]

  def domain(model: OpenFeedModel) = {
    OpenFeed(
      model.zone,
      model.siteCode,
      Util.md5Hash(UUID.randomUUID().toString()),
      model.feedLink,
      model.feedSite,
      model.siteLogo,
      model.filter)
  }
}