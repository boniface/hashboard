package models

import java.util.UUID

import conf.Util
import domain.Feed
import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class FeedModel( zone:String,
                       feedLink: String,
                       feedType: String,
                       feedSite: String,
                       siteLogo: String,
                       siteCode:String) {
  def getDomain(): Feed = FeedModel.domain(this)
}

object FeedModel {
  implicit val roleFmt = Json.format[FeedModel]

  def domain(model: FeedModel) = {
    Feed(model.zone,Util.md5Hash(UUID.randomUUID().toString()), model.feedLink, model.feedType, model.feedSite, model.siteLogo,model.siteCode)
  }
}