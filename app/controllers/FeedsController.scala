package controllers

import models.FeedModel
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.feeds.FeedsService
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/01.
 */
object FeedsController extends Controller {

  def create(zone: String) = Action.async(parse.json) {

    request =>
      val input = request.body
      val feedsModel = Json.fromJson[FeedModel](input).get

      val f = feedsModel.getDomain()
      val feed = f.copy(zone = zone)
      val results = FeedsService.save(feed)
      results.map(result =>
        Ok(Json.toJson(feed)))
  }

  def update(zone: String, feedId: String) = Action.async(parse.json) {
    request =>
      val input = request.body
      val feedsModel = Json.fromJson[FeedModel](input).get
      val f = feedsModel.getDomain()
      val feed = f.copy(zone = zone, id = feedId)
      val results = FeedsService.save(feed)
      results.map(result =>
        Ok(Json.toJson(feed)))
  }

  def getFeedById(zone: String, id: String) = Action.async {
    request =>
      FeedsService.getFeedById(zone, id) map (feed => Ok(Json.toJson(feed)))
  }

  def getFeeds = Action.async {
    request =>
      FeedsService.getFeeds map (feeds => Ok(Json.toJson(feeds)))
  }

  def getFeedsByZone(zone: String) = Action.async {
    request =>
      FeedsService.getFeedsByZone(zone) map (feeds => Ok(Json.toJson(feeds)))
  }

  def deleteFeed(zone: String, id: String) = Action.async {
    request =>
      FeedsService.delete(zone, id) map (_ => Ok(Json.toJson("OK")))
  }
}
