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
    service.save(site)
  }

  def delete(zone:String, site: String) = {

    service.deleteSiteById(zone,site)
  }

  def getsiteById(zone:String, site: String) = {
    service.getSiteById(zone, site)
  }

  def getSites(start:Int, limit:Int) = {
    SiteRepository.getSitesByNumber(start,limit)
  }

  def getSitesByZone(zone:String)={
    SiteRepository.getSitesByZone(zone)
  }

}
