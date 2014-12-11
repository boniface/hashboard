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
      val feed = s.copy(zone = zone)
      val results = SiteService.save(feed)
      results.map(result =>
        Ok(Json.toJson(feed)))
  }
  def update(zone: String, feedId: String) = Action.async(parse.json) {
    request =>
      val input = request.body
      val feedsModel = Json.fromJson[SiteModel](input).get
      val f = feedsModel.getDomain()
      val feed = f.copy(zone = zone, id = feedId)
      val results = SiteService.save(feed)
      results.map(result =>
        Ok(Json.toJson(feed)))
  }
  def getSiteById(zone: String, id: String) = Action.async {
    request =>
      SiteService.getsiteById(zone, id) map (site => Ok(Json.toJson(site)))
  }
  def getSites(startvalue: String) = Action.async {
    request =>
      val start:Int = Util.getIntFromString(startvalue)
      SiteService.getSites(start, 100) map (sites => Ok(Json.toJson(sites.toSeq)))
  }
  def getSitesByZone(zone: String) = Action.async {
    request =>
      SiteService.getSitesByZone(zone) map (feeds => Ok(Json.toJson(feeds)))
  }

  def deleteSiteById(zone: String, id: String) = Action.async {
    request =>
      SiteService.delete(zone, id) map (_ => Ok(Json.toJson("OK")))
  }
}