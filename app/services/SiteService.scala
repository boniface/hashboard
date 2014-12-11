package services

import domain.Site
import respository.SiteRepository

/**
 * Created by hashcode on 2014/12/01.
 */
object SiteService {
  def service = SiteRepository

  def save(site: Site) = {
    service.save(site)
  }

  def update(site: Site) = {
    service.updateSite(site.id, site)
  }

  def delete(zone:String, id: String) = {
    service.deleteSiteById(zone,id)
  }

  def getsiteById(zone:String, id: String) = {
    service.getSiteById(zone,id)
  }

  def getSites(start:Int, limit:Int) = {
    SiteRepository.getSitesByNumber(start,limit)
  }

  def getSitesByZone(zone:String)={
    SiteRepository.getSitesByZone(zone)
  }

}
