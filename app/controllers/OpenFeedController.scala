package controllers

import models.OpenFeedModel
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.feeds.OpenFeedsService
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2015/02/05.
 */
object OpenFeedController extends Controller {

  def create(zone: String) = Action.async(parse.json) {

    request =>
      val input = request.body
      val openFeedModel = Json.fromJson[OpenFeedModel](input).get
      val f = openFeedModel.getDomain()
      val openFeed = f.copy(zone = zone)
      val results = OpenFeedsService.save(openFeed)
      results.map(result =>
        Ok(Json.toJson(openFeed)))
  }

  def update(zone: String, feedId: String) = Action.async(parse.json) {
    request =>
      val input = request.body
      val feedsModel = Json.fromJson[OpenFeedModel](input).get
      val f = feedsModel.getDomain()
      val feed = f.copy(zone = zone, id = feedId)
      val results = OpenFeedsService.save(feed)
      results.map(result =>
        Ok(Json.toJson(feed)))
  }

  def getCustomFeedById(zone: String, sitecode: String, id: String) = Action.async {
    request =>
      OpenFeedsService.getFeedById(zone, sitecode, id) map (feed => Ok(Json.toJson(feed)))
  }

  def getAllCustomFeeds = Action.async {
    request =>
      OpenFeedsService.getAllOpenFeeds map (feeds => Ok(Json.toJson(feeds)))
  }

  def getCustomFeedsByZone(zone: String) = Action.async {
    request =>
      OpenFeedsService.getFeedsByZone(zone) map (feeds => Ok(Json.toJson(feeds)))
  }

  def deleteFeed(zone: String, sitecode: String, id: String) = Action.async {
    request =>
      OpenFeedsService.delete(zone, sitecode, id) map (_ => Ok(Json.toJson("OK")))
  }
}
