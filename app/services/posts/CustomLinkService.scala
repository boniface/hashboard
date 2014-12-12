package services.posts

import respository.CustomLinkRepository

/**
 * Created by hashcode on 2014/12/12.
 */
object CustomLinkService {
  def getLatestCustomLinks(zone:String)= {
    CustomLinkRepository.getLatestLinks(zone)
  }
}
