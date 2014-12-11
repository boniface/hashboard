package conf

import com.datastax.driver.core.{Session, Cluster}
import com.websudos.phantom.Implicits._

import scala.concurrent._

/**
 * Created by hashcode on 2014/12/01.
 */
object DataConnection {
  val keySpace = "hashmedia"

  lazy val cluster = Cluster.builder()
    .addContactPoint("localhost")
    .withPort(9042)
    .withoutJMXReporting()
    .withoutMetrics()
    .build()

  lazy val session = blocking {
    cluster.connect(keySpace)
  }
}

trait DataConnection {
  self: CassandraTable[_, _] =>

  def createTable(): Future[Unit] = {
    create.future() map (_ => ())
  }

  implicit lazy val datastax: Session = DataConnection.session
}