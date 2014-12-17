package respository

import java.util.Date

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.column.DateColumn
import com.websudos.phantom.iteratee.Iteratee
import conf.DataConnection
import domain.CustomLink
import org.joda.time.DateTime

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/10/19.
 */
class CustomLinkRepository extends CassandraTable[CustomLinkRepository, CustomLink] {

  object zone extends StringColumn(this) with PartitionKey[String]
  object datepublished extends DateColumn(this) with PrimaryKey[Date] with ClusteringOrder[Date] with Descending
  object linkhash extends StringColumn(this) with PrimaryKey[String] with ClusteringOrder[String] with Descending

  object url extends StringColumn(this)
  object site extends StringColumn(this)
  object siteCode extends StringColumn(this)
  object title extends StringColumn(this)

  override def fromRow(row: Row): CustomLink = {
    CustomLink(zone(row),
      datepublished(row),
      linkhash(row),
      url(row),
      site(row),
      siteCode(row),
      title(row))
  }
}

object CustomLinkRepository extends CustomLinkRepository with DataConnection {
  override lazy val tableName = "clinks"

  def save(link: CustomLink): Future[ResultSet] = {
    insert
      .value(_.linkhash, link.linkhash)
      .value(_.site, link.site)
      .value(_.url, link.url)
      .value(_.zone, link.zone)
      .value(_.datepublished, link.datePublished)
      .value(_.siteCode, link.siteCode)
      .value(_.title, link.title)
      .future() flatMap {
      _ =>{
        CustomProcessedLinkskRepository.insert
          .value(_.linkhash,link.linkhash)
          .future()
      }}
  }
  def getLatestLinks(zone: String): Future[Seq[CustomLink]] = {
    val date = DateTime.now.minusHours(2).toDate
    select.where(_.zone eqs zone).and(_.datepublished gte date).fetchEnumerator() run Iteratee.collect()
  }

  def getPostedLinks(zone: String): Future[Iterator[CustomLink]]= {
    select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.slice(0, 50)
  }
}
