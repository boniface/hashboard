package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits.{StringColumn, _}
import com.websudos.phantom.keys.PartitionKey
import conf.DataConnection
import domain.HashTag

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/05/01.
 */
class HashTagRepository  extends CassandraTable[HashTagRepository, HashTag] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object description extends StringColumn(this)

  object hashtag extends StringColumn(this)


  override def fromRow(row: Row): HashTag = {
    HashTag(
      zone(row),
      description(row),
      hashtag(row))
  }
}

object HashTagRepository extends HashTagRepository with  DataConnection {
  override lazy val tableName = "hashtags"

  def save(hashtag: HashTag): Future[ResultSet] = {
    insert
      .value(_.zone, hashtag.zone)
      .value(_.description, hashtag.description)
      .value(_.hashtag, hashtag.hashtag)
      .future()
  }

  def getZoneHashTags(zone: String): Future[Option[HashTag]] = {
    select.where(_.zone eqs zone).one()
  }

  def getZoneHashTags: Future[Option[HashTag]] = {
    select.where(_.zone eqs zone).one()
  }

}
