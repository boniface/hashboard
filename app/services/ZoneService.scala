package services

import domain.Zone
import respository.ZoneRespository

/**
 * Created by hashcode on 2014/12/01.
 */
object ZoneService {
  def createZone(zone:Zone) = {
    ZoneRespository.save(zone)
  }
  def getZone(id:String) = {
    ZoneRespository.getZoneById(id)
  }

  def getZones = {
    ZoneRespository.getAllZones
  }

  def updateZone(zone:Zone) = {
    ZoneRespository.updateZone(zone)
  }

  def deleteZone(id:String) = {
    ZoneRespository.deleteZoneById(id)
  }

  def updateZoneStatus(id:String, status:String) = {
    ZoneRespository.updateStatus(id,status)
  }
}
