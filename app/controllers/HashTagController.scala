package controllers

import domain.HashTag
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.HashTagService

/**
 *
 * Created by hashcode on 2015/05/06.
 */
object HashTagController extends Controller {
  def create(tag: String) = Action.async(parse.json) {
    request =>
      val input = request.body
      val hashTag = Json.fromJson[HashTag](input).get
      val results = HashTagService.save(hashTag)
      results.map(result =>
        Ok(Json.toJson(hashTag)))
  }

  def getTags =Action.async {
    request =>
      HashTagService.getAllZoneTags map (tags => Ok(Json.toJson(tags)))
  }

  def getZoneTags(zone:String) = Action.async {
    request =>
      HashTagService.getZoneTags(zone) map (tag => Ok(Json.toJson(tag)))
  }

}
