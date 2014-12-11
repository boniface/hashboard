package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.iteratee.Iteratee
import conf.{DataConnection, Util}
import domain.{Feed, Stats}

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/12/01.
 */
class FeedsRespository extends CassandraTable[FeedsRespository, Feed] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object id extends StringColumn(this) with PrimaryKey[String]

  object feedLink extends StringColumn(this)

  object feedType extends StringColumn(this)

  object feedSite extends StringColumn(this)

  object siteLogo extends StringColumn(this)

  object siteCode extends StringColumn(this)

  override def fromRow(row: Row): Feed = {
    Feed(
      zone(row),
      id(row),
      feedLink(row),
      feedType(row),
      feedSite(row),
      siteLogo(row),
      siteCode(row))


  }
}

object FeedsRespository extends FeedsRespository with DataConnection {
  override lazy val tableName = "feeds"

  def save(feed: Feed): Future[ResultSet] = {
    insert
      .value(_.id, feed.id)
      .value(_.feedLink, feed.feedLink)
      .value(_.feedType, feed.feedType)
      .value(_.feedSite, feed.feedSite)
      .value(_.siteLogo, feed.siteLogo)
      .value(_.zone, feed.zone)
      .value(_.siteCode, feed.siteCode)
      .future()
    StatsRepository.statIncrement(Stats(feed.id, feed.zone + Util.FEED.toString, 1L))
  }

  def getFeedById(zone: String, id: String): Future[Option[Feed]] = {
    select.where(_.zone eqs zone).and(_.id eqs id).one();
  }

  def getFeedsByZone(zone: String): Future[Seq[Feed]] = {
    select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.collect()
  }

  def getFeeds: Future[Seq[Feed]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def deleteFeed(zone: String, id: String): Future[ResultSet] = {
    delete.where(_.zone eqs zone).and(_.id eqs id).future();
    StatsRepository.statDecrement(Stats(id, zone + Util.FEED.toString, 1L))
  }

  def updateFeed(feed: Feed) = {
    // NOT NECESSARRY
    update.where(_.zone eqs feed.zone).and(_.id eqs feed.id).
      modify(_.feedLink setTo feed.feedLink)
      .and(_.feedType setTo feed.feedType)
      .and(_.feedSite setTo feed.feedSite)
      .and(_.siteLogo setTo feed.siteLogo)
      .future()
  }

}
