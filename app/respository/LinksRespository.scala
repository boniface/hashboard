/*
 * Copyright (c) 2014 Hashcode (Z) Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package respository

import java.util.Date

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.Implicits._
import com.websudos.phantom.iteratee.Iteratee
import conf.DataConnection
import domain.Link
import org.joda.time.DateTime

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/07/12.
 */
class LinksRespository extends CassandraTable[LinksRespository, Link] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object datepublished extends DateColumn(this) with PrimaryKey[Date] with ClusteringOrder[Date] with Descending

  object linkhash extends StringColumn(this) with PrimaryKey[String] with ClusteringOrder[String] with Descending

  object url extends StringColumn(this)

  object site extends StringColumn(this)

  object siteCode extends StringColumn(this)

  object title extends StringColumn(this)

  object linkType extends StringColumn(this)

  override def fromRow(row: Row): Link = {
    Link(zone(row),
      linkhash(row),
      datepublished(row),
      url(row),
      site(row),
      siteCode(row),
      title(row),
      linkType(row))
  }
}

object LinksRespository extends LinksRespository with DataConnection {
  override lazy val tableName = "links"

  def save(link: Link): Future[ResultSet] = {
    insert
      .value(_.linkhash, link.linkhash)
      .value(_.site, link.site)
      .value(_.url, link.url)
      .value(_.zone, link.zone)
      .value(_.datepublished, link.datePublished)
      .value(_.siteCode, link.siteCode)
      .value(_.title, link.title)
      .value(_.linkType, link.linkType)
      .future()
  }

  def getLinkById(linkhash: String): Future[Option[Link]] = {
    select.where(_.linkhash eqs linkhash).one()
  }

  def getAllLinks: Future[Seq[Link]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  def getLinksByDate(): Future[Seq[Link]] = {
    val date = DateTime.now().minusHours(2).toDate
    select.where(_.datepublished gte date).fetchEnumerator() run Iteratee.collect()
  }

  def getLatestLinks(zone: String): Future[Seq[Link]] = {
    val date = DateTime.now.minusHours(2).toDate
    select.where(_.zone eqs zone).and(_.datepublished gte date).fetchEnumerator() run Iteratee.collect()
  }

  def getPostedLinks(zone: String): Future[Iterator[Link]]= {
    select.where(_.zone eqs zone).orderBy(_.datepublished.desc).fetchEnumerator() run Iteratee.slice(0, 100)
  }
}



