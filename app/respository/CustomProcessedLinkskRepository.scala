package respository

import com.datastax.driver.core.ResultSet
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import conf.DataConnection
import domain.CustomProcessedLinks

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/10/19.
 */
class CustomProcessedLinkskRepository extends CassandraTable[CustomProcessedLinkskRepository, CustomProcessedLinks] {

  object linkhash extends StringColumn(this) with PartitionKey[String]


  override def fromRow(row: Row): CustomProcessedLinks = {
    CustomProcessedLinks(
      linkhash(row))
  }
}

object CustomProcessedLinkskRepository extends CustomProcessedLinkskRepository with DataConnection {
  override lazy val tableName = "cplinks"

  def save(link: CustomProcessedLinks): Future[ResultSet] = {
    insert
      .value(_.linkhash, link.linkhash)
      .future()
  }

  def getLinkById(linkhash: String): Future[Option[CustomProcessedLinks]] = {
    select.where(_.linkhash eqs linkhash).one()
  }


}


