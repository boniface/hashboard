package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.iteratee.Iteratee
import conf.DataConnection
import domain.Site

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/12/01.
 */
class SiteRepository extends CassandraTable[SiteRepository, Site] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object url extends StringColumn(this) with PrimaryKey[String]

  object code extends StringColumn(this)

  object logo extends StringColumn(this)

  override def fromRow(row: Row): Site = {
    Site(zone(row), url(row), code(row), logo(row))
  }
}

object SiteRepository extends SiteRepository with DataConnection {
  override lazy val tableName = "sites"

  def save(site: Site): Future[ResultSet] = {
    insert
      .value(_.url, site.url)
      .value(_.code, site.code)
      .value(_.zone, site.zone)
      .value(_.logo, site.logo)
      .future()
  }

  def getSiteById(zone: String, site: String): Future[Option[Site]] = {
    select.where(_.zone eqs zone).and(_.url eqs site).one()
  }

  def deleteSiteById(zone:String,site: String): Future[ResultSet] = {
    delete.where(_.zone eqs zone).and(_.url eqs site).future()
  }

  def getSitesByNumber(start: Int, limit: Int): Future[Iterator[Site]] = {
    select.fetchEnumerator() run Iteratee.slice(start, limit)
  }

  def getSitesByZone(zone: String): Future[Seq[Site]] = {
    select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.collect()
  }
}
