package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.LinksService

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/13.
 */
object LinksController extends Controller{

  def getCollectedLinks(zone:String)= Action.async{
    request =>
    LinksService.getPostLinks(zone) map (links =>
      Ok(Json.toJson(links.toSeq)))
  }

  def getCustomCollectedLinks(zone:String) = Action.async{
    request =>
      LinksService.getCustomLinks(zone) map (links =>
        Ok(Json.toJson(links.toSeq)))
  }


}
