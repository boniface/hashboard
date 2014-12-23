package controllers

import conf.Util
import models.SiteModel
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.SiteService

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/01.
 */
object SiteController extends Controller {
  def create(zone: String) = Action.async(parse.json) {
    request =>
      val input = request.body
      val siteModel = Json.fromJson[SiteModel](input).get
      val s = siteModel.getDomain()
      val site = s.copy(zone = zone)
      val results = SiteService.save(site)
      results.map(result =>
        Ok(Json.toJson(site)))
  }
  def update(zone: String, url: String) = Action.async(parse.json) {
    request =>
      val input = request.body
      val siteModel = Json.fromJson[SiteModel](input).get
      val f = siteModel.getDomain()
      val site = f.copy(zone = zone, url = url)
      val results = SiteService.save(site)
      results.map(result =>
        Ok(Json.toJson(site)))
  }
  def getSiteById(zone: String, site: String) = Action.async {
    request =>
      SiteService.getsiteById(zone, site) map (site => Ok(Json.toJson(site)))
  }
  def getSites(startvalue: String) = Action.async {
    request =>
      val start:Int = Util.getIntFromString(startvalue)
      SiteService.getSites(start, 100) map (sites => Ok(Json.toJson(sites.toSeq)))
  }
  def getSitesByZone(zone: String) = Action.async {
    request =>
      SiteService.getSitesByZone(zone) map (sites => Ok(Json.toJson(sites)))
  }

  def deleteSiteById(zone: String, site: String) = Action.async {
    request =>
      SiteService.delete(zone, site) map (_ => Ok(Json.toJson("OK")))
  }
}