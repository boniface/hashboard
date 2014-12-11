package services

import domain.SocialMediaFeed
import respository.SocialMediaFeedsRespository

/**
 * Created by hashcode on 2014/12/01.
 */
object SocialMediaFeedsService {
  def getAllSocialMediaFeeds = {
    SocialMediaFeedsRespository.getAllZoneFeeds
  }

  def save(feed: SocialMediaFeed) = {
    SocialMediaFeedsRespository.save(feed)
  }

  def getFeedById(zone: String, id: String) = {
    SocialMediaFeedsRespository.getFeedByZone(zone, id)
  }

  def getFeedsByZone(zone: String) = {
    SocialMediaFeedsRespository.getAllZoneFeeds(zone)
  }

  def delete(zone: String, id: String) = {
    SocialMediaFeedsRespository.deleteFeed(zone, id)
  }

}
