package models

import java.util.UUID

import conf.Util
import domain.CustomFeed
import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class CustomFeedModel(zone: String,
                           siteCode: String,
                           feedLink: String,
                           feedSite: String,
                           siteLogo: String,
                           filter:String
                            ){
  def getDomain(): CustomFeed = CustomFeedModel.domain(this)
}

object CustomFeedModel {
  implicit val customFeedFmt = Json.format[CustomFeedModel]

  def domain(model: CustomFeedModel) = {
    CustomFeed(
      model.zone,
      model.siteCode,
      Util.md5Hash(UUID.randomUUID().toString()),
      model.feedLink,
      model.feedSite,
      model.siteLogo,
      model.filter)
  }
}