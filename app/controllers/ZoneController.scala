package controllers

import models.ZoneModel
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.ZoneService
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/01.
 */
object ZoneController extends Controller {

  def create = Action.async(parse.json) {
    request =>
      val input = request.body
      val zoneModel = Json.fromJson[ZoneModel](input).get
      val zone = zoneModel.getDomain()
      val results = ZoneService.createZone(zone)
      results.map(result =>
        Ok(Json.toJson(zone)))
  }

  def update(code: String) = Action.async(parse.json) {
    request =>
      val input = request.body
      val zoneModel = Json.fromJson[ZoneModel](input).get
      val f = zoneModel.getDomain()
      val zone = f.copy(code = code)
      val results = ZoneService.updateZone(zone)
      results.map(result =>
        Ok(Json.toJson(zone)))
  }
  def getZoneById(code: String) = Action.async {
    request =>
      ZoneService.getZone(code) map (zone => Ok(Json.toJson(zone)))
  }

  def getZones = Action.async {
    request =>
      ZoneService.getZones map (zones => Ok(Json.toJson(zones)))
  }

  def deleteZone(code: String) = Action.async {
    request =>
      ZoneService.deleteZone(code) map (_ => Ok(Json.toJson("OK")))
  }

  def updateZoneStatus(id: String, status: String) = Action.async {
    request =>
      ZoneService.updateZoneStatus(id, status) map (_ => Ok(Json.toJson("OK")))
  }
}
