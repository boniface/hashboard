package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.keys.PartitionKey
import conf.DataConnection
import domain.{Link, PublishedLinks}

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/02/06.
 */
class PublishedLinksRepository extends CassandraTable[PublishedLinksRepository, PublishedLinks] {

  object item extends StringColumn(this) with PartitionKey[String]

  object linkhash extends StringColumn(this) with PrimaryKey[String]

  override def fromRow(row: Row): PublishedLinks = {
    PublishedLinks(
      item(row),
      linkhash(row)
    )
  }
}

object PublishedLinksRepository extends PublishedLinksRepository with DataConnection {
  override lazy val tableName = "publinks"
  def save(link: PublishedLinks): Future[ResultSet] = {
    insert
      .value(_.linkhash, link.linkhash)
      .value(_.item, link.item)
      .ttl(604800)
      .future()
  }

  def getLinkById(item: String, linkhash: String): Future[Option[PublishedLinks]] = {
    select.where(_.item eqs item).and(_.linkhash eqs linkhash).one()
  }


}
