package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.PostsService
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/13.
 */
object PostsController extends Controller{
  def getPostTitles(zone:String)= Action.async{
    request =>
    PostsService.getTodayZonePosts(zone) map (posts=>
      Ok(Json.toJson(posts.toSeq)))
  }
}
