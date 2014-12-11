package services.feeds

import domain.CustomFeed
import respository.CustomFeedRepository

/**
 * Created by hashcode on 2014/12/01.
 */
object CustomFeedsService {
  def getAllCustomFeeds = {
    CustomFeedRepository.getFeeds
  }

  def save(feed: CustomFeed) = {
    CustomFeedRepository.save(feed)
  }

  def getFeedById(zone: String, sitecode:String, id: String) = {
    CustomFeedRepository.getFeedById(zone, sitecode,id)
  }

  def getFeedsByZone(zone: String) = {
    CustomFeedRepository.getFeedsByZone(zone)
  }

  def delete(zone: String, sitecode:String,id: String) = {
    CustomFeedRepository.deleteFeed(zone, sitecode,id)
  }
}
