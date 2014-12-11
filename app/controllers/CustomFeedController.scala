package controllers

import models.CustomFeedModel
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.feeds.CustomFeedsService
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/01.
 */
object CustomFeedController extends Controller {

  def create(zone: String) = Action.async(parse.json) {

    request =>
      val input = request.body
      val customFeedModel = Json.fromJson[CustomFeedModel](input).get
      val f = customFeedModel.getDomain()
      val customFeed = f.copy(zone = zone)
      val results = CustomFeedsService.save(customFeed)
      results.map(result =>
        Ok(Json.toJson(customFeed)))
  }
  def update(zone: String, feedId: String) = Action.async(parse.json) {
    request =>
      val input = request.body
      val feedsModel = Json.fromJson[CustomFeedModel](input).get
      val f = feedsModel.getDomain()
      val feed = f.copy(zone = zone, id = feedId)
      val results = CustomFeedsService.save(feed)
      results.map(result =>
        Ok(Json.toJson(feed)))
  }

  def getCustomFeedById(zone: String, sitecode: String, id: String) = Action.async {
    request =>
      CustomFeedsService.getFeedById(zone, sitecode, id) map (feed => Ok(Json.toJson(feed)))
  }

  def getAllCustomFeeds = Action.async {
    request =>
      CustomFeedsService.getAllCustomFeeds map (feeds => Ok(Json.toJson(feeds)))
  }

  def getCustomFeedsByZone(zone: String) = Action.async {
    request =>
      CustomFeedsService.getFeedsByZone(zone) map (feeds => Ok(Json.toJson(feeds)))
  }

  def deleteFeed(zone: String, sitecode:String,id: String) = Action.async {
    request =>
      CustomFeedsService.delete(zone, sitecode,id) map (_ => Ok(Json.toJson("OK")))
  }
}
