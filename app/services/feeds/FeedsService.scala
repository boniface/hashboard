package services.feeds

import domain.Feed
import respository.FeedsRespository

/**
 * Created by hashcode on 2014/12/01.
 */
object FeedsService {

  def save(feed: Feed) = {
    FeedsRespository.save(feed)
  }
  def getFeedById(zone:String,id: String) = {
    FeedsRespository.getFeedById(zone,id)
  }
  def getFeedsByZone(zone:String) = {
    FeedsRespository.getFeedsByZone(zone)
  }
  def getFeeds = {
    FeedsRespository.getFeeds
  }
  def delete(zone:String,id: String) = {
    FeedsRespository.deleteFeed(zone,id)
  }



}
