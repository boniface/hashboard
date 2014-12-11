package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.iteratee.Iteratee
import conf.DataConnection
import domain.Zone

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/12/01.
 */
class ZoneRespository extends CassandraTable[ZoneRespository, Zone] {

  object code extends StringColumn(this) with PartitionKey[String]

  object name extends StringColumn(this)

  object status extends StringColumn(this)

  object flag extends StringColumn(this)

  override def fromRow(row: Row): Zone = {
    Zone(
      code(row),
      name(row),
      status(row),
      flag(row)
    )
  }
}

object ZoneRespository extends ZoneRespository with DataConnection {
  override lazy val tableName = "zones"

  def save(zone: Zone): Future[ResultSet] = {
    insert
      .value(_.name, zone.name)
      .value(_.code, zone.code)
      .value(_.status, zone.status)
      .value(_.flag, zone.flag)
      .future()
  }

  def getZoneById(code: String): Future[Option[Zone]] = {
    select.where(_.code eqs code).one()
  }

  def getAllZones: Future[Seq[Zone]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }
  def updateZone(zone: Zone): Future[ResultSet] = {
    update.where(_.code eqs zone.code)
      .modify(_.name setTo zone.name)
      .and(_.status setTo zone.status)
      .future()
  }

  def updateStatus(code: String, status: String): Future[ResultSet] = {
    update.where(_.code eqs code)
      .modify(_.status setTo status)
      .future()
  }

  def deleteZoneById(code: String): Future[ResultSet] = {
    delete.where(_.code eqs code).future()
  }
}


