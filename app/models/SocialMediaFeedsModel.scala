package models

import java.util.UUID

import conf.Util
import domain.SocialMediaFeed
import play.api.libs.json.Json

/**
 * Created by hashcode on 2014/12/01.
 */
case class SocialMediaFeedsModel(zone: String,
                                 feedLink: String,
                                 feedType: String,
                                 feedSite: String,
                                 siteLogo: String,
                                 siteCode: String) {
  def getDomain(): SocialMediaFeed = SocialMediaFeedsModel.domain(this)
}

object SocialMediaFeedsModel {
  implicit val smFeedFmt = Json.format[SocialMediaFeedsModel]

  def domain(model: SocialMediaFeedsModel) = {
    SocialMediaFeed(model.zone, Util.md5Hash(UUID.randomUUID().toString()), model.feedLink, model.feedType, model.feedSite, model.siteLogo, model.siteCode)
  }
}

