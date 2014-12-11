package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.iteratee.Iteratee
import com.websudos.phantom.keys.PrimaryKey
import conf.{DataConnection, Util}
import domain.{CustomFeed, Stats}

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/12/01.
 */
class CustomFeedRepository extends CassandraTable[CustomFeedRepository, CustomFeed] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object siteCode extends StringColumn(this) with PrimaryKey[String]

  object id extends StringColumn(this) with PrimaryKey[String]

  object feedLink extends StringColumn(this)

  object feedSite extends StringColumn(this)

  object siteLogo extends StringColumn(this)

  object filter extends StringColumn(this)


  override def fromRow(row: Row): CustomFeed = {
    CustomFeed(
      zone(row),
      siteCode(row),
      id(row),
      feedLink(row),
      feedSite(row),
      siteLogo(row),
      filter(row)
    )
  }
}

object CustomFeedRepository extends CustomFeedRepository with DataConnection {
  override lazy val tableName = "cfeeds"
  def save(feed: CustomFeed): Future[ResultSet] = {
    insert
      .value(_.zone, feed.zone)
      .value(_.id, feed.id)
      .value(_.siteCode, feed.siteCode)
      .value(_.feedLink, feed.feedLink)
      .value(_.feedSite, feed.feedSite)
      .value(_.siteLogo, feed.siteLogo)
      .value(_.filter, feed.filter)
      .future()
  }

  def getFeedById(zone: String, siteCode: String, id: String): Future[Option[CustomFeed]] = {
    select.where(_.zone eqs zone).and(_.siteCode eqs siteCode).and(_.id eqs id).one();
  }

  def getFeedsBySite(zone: String, siteCode: String): Future[Seq[CustomFeed]] = {
    select.where(_.zone eqs zone).and(_.siteCode eqs siteCode).fetchEnumerator() run Iteratee.collect()
  }

  def getFeedsByZone(zone: String): Future[Seq[CustomFeed]] = {
    select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.collect()
  }

  def getFeeds: Future[Seq[CustomFeed]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteFeed(zone: String, sitecode:String,id: String): Future[ResultSet] = {
    delete.where(_.zone eqs zone).and(_.siteCode eqs sitecode).and(_.id eqs id).future();
    StatsRepository.statDecrement(Stats(id, zone + Util.FEED.toString, 1L))
  }
}