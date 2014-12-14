package services

import respository.{CustomLinkRepository, LinksRespository}

/**
 * Created by hashcode on 2014/12/13.
 */
object LinksService {
  def getPostLinks(zone:String)={
    LinksRespository.getPostedLinks(zone)
  }

  def getCustomLinks(zone:String) = {
    CustomLinkRepository.getPostedLinks(zone)
  }

}
