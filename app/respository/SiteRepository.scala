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

  object id extends StringColumn(this) with PrimaryKey[String]

  object zone extends StringColumn(this) with PartitionKey[String]

  object name extends StringColumn(this)

  object url extends StringColumn(this)

  object description extends StringColumn(this)

  object logo extends StringColumn(this)

  override def fromRow(row: Row): Site = {
    Site(id(row), zone(row), name(row), url(row), description(row), logo(row))
  }
}

object SiteRepository extends SiteRepository with DataConnection {
  override lazy val tableName = "sites"

  def save(site: Site): Future[ResultSet] = {
    insert
      .value(_.id, site.id)
      .value(_.name, site.name)
      .value(_.url, site.url)
      .value(_.description, site.description)
      .value(_.zone, site.zone)
      .value(_.logo, site.logo)
      .future()
  }

  def getSiteById(zone: String, siteId: String): Future[Option[Site]] = {
    select.where(_.zone eqs zone).and(_.id eqs siteId).one();
  }

  def deleteSiteById(zone:String,siteId: String): Future[ResultSet] = {
    delete.where(_.zone eqs zone).and(_.id eqs siteId).future();
  }

  def updateSite(siteId: String, site: Site) = {
    update.where(_.id eqs siteId)
      .modify(_.name setTo site.name)
      .and(_.url setTo site.url)
      .and(_.description setTo site.description)
      .future()
  }

  def getSitesByNumber(start: Int, limit: Int): Future[Iterator[Site]] = {
    select.fetchEnumerator() run Iteratee.slice(start, limit)
  }

  def getSitesByZone(zone: String): Future[Seq[Site]] = {
    select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.collect()
  }
}
