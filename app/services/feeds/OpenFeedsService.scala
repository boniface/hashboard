package services.feeds

import domain.OpenFeed
import respository.OpenFeedRepository

/**
 * Created by hashcode on 2015/02/05.
 */
object OpenFeedsService {
  def getAllOpenFeeds = {
    OpenFeedRepository.getFeeds
  }

  def save(feed: OpenFeed) = {
    OpenFeedRepository.save(feed)
  }

  def getFeedById(zone: String, sitecode:String, id: String) = {
    OpenFeedRepository.getFeedById(zone, sitecode,id)
  }

  def getFeedsByZone(zone: String) = {
    OpenFeedRepository.getFeedsByZone(zone)
  }

  def delete(zone: String, sitecode:String,id: String) = {
    OpenFeedRepository.deleteFeed(zone, sitecode,id)
  }

}
