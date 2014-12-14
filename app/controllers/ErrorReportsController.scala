package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.ErrorReportsService
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/13.
 */
object ErrorReportsController extends Controller{

  def getErrorReport(zone:String)= Action.async{
    request =>
      ErrorReportsService.getErrorreports(zone) map ( error =>
        Ok(Json.toJson(error.toSeq)))
  }

}
