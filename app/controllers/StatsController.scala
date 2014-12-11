package controllers

import play.api.libs.json.Json
import play.api.mvc.{Controller, Action}
import services.StatsService
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/01.
 */
object StatsController extends Controller{
  def getStats(item: String, subjectId: String) = Action.async {
    request =>
      StatsService.getStats(item, subjectId) map
        (stat => Ok(Json.toJson(stat match {
          case Some(stat) => stat.counter
          case None => 0
        })))
  }

}
