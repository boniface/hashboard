package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.keys.PartitionKey
import conf.DataConnection
import domain.{Link, Social}

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/02/09.
 */
class SocialRepository extends CassandraTable[SocialRepository, Social] {

  object service extends StringColumn(this) with PartitionKey[String]

  object props extends MapColumn[SocialRepository, Social, String, String](this)

  override def fromRow(row: Row): Social = {
    Social(
      service(row),
      props(row)
    )
  }
}

object SocialRepository extends SocialRepository with DataConnection {
  override lazy val tableName = "social"

  def save(social: Social): Future[ResultSet] = {
    insert
      .value(_.service, social.service)
      .value(_.props, social.props)
      .future()
  }

  def getSocialService(service: String): Future[Option[Social]] = {
    select.where(_.service eqs service).one()
  }


}
