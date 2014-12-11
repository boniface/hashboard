package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.column.CounterColumn
import com.websudos.phantom.keys.PartitionKey
import conf.DataConnection
import domain.Stats

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/12/01.
 */
class StatsRepository extends CassandraTable[StatsRepository, Stats] {

  object id extends StringColumn(this) with PartitionKey[String]

  object item extends StringColumn(this) with PrimaryKey[String]

  object counter extends CounterColumn(this)

  override def fromRow(row: Row): Stats = {
    Stats(
      id(row),
      item(row),
      counter(row)
    )
  }
}

object StatsRepository extends StatsRepository with DataConnection {
  override lazy val tableName = "stats"
  def statIncrement(stat: Stats): Future[ResultSet] = {
    update.where(_.id eqs stat.id).and(_.item eqs stat.item).modify(_.counter increment 1L).future()
  }
  def statDecrement(stat: Stats): Future[ResultSet] = {
    update.where(_.id eqs stat.id).and(_.item eqs stat.item).modify(_.counter decrement 1L).future()
  }
  def getStats(item: String, id: String): Future[Option[Stats]] = {
    select.where(_.id eqs id).and(_.item eqs item).one()
  }

}
