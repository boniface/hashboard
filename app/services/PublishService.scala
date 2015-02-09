package services

import respository.PublishedLinksRepository

/**
 * Created by hashcode on 2015/02/06.
 */
object PublishService {

  def isPublished( service:String,link:String) ={
    PublishedLinksRepository.getLinkById(service,link)
  }
}
