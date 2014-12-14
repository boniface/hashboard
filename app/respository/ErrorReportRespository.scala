package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.column.DateColumn
import com.websudos.phantom.iteratee.Iteratee
import conf.DataConnection
import domain.ErrorReport
import com.websudos.phantom.Implicits._

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/12/06.
 */
class ErrorReportRespository extends CassandraTable[ErrorReportRespository, ErrorReport] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object id extends StringColumn(this) with PrimaryKey[String]

  object site extends StringColumn(this)

  object date extends DateColumn(this)

  object message extends StringColumn(this)

  override def fromRow(row: Row): ErrorReport = {
    ErrorReport(zone(row), id(row), site(row), date(row), message(row))
  }
}

object ErrorReportRespository extends ErrorReportRespository with DataConnection {
  override lazy val tableName = "ereports"

  def save(ereport: ErrorReport): Future[ResultSet] = {
    insert
      .value(_.zone, ereport.zone)
      .value(_.site, ereport.site)
      .value(_.date, ereport.date)
      .value(_.id, ereport.id)
      .value(_.message, ereport.message)
      .future()
  }

  def getErrorReport(zone: String): Future[Iterator[ErrorReport]]= {
       select.where(_.zone eqs zone).fetchEnumerator() run Iteratee.slice(0, 100)
  }
}
