package services

import domain.HashTag
import respository.HashTagRepository

/**
 * Created by hashcode on 2015/05/06.
 */
object HashTagService {

  def service = HashTagRepository

  def save(tag: HashTag) = {
    service.save(site)
  }

  def getZoneTags(zone:String) ={
    service.getZoneHashTags()
  }

  def getAllZoneTags={
    service.getZoneHashTags
  }




}
