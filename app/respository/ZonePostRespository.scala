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
import domain.Post
import org.joda.time.DateTime

import scala.concurrent.Future


class ZonePostRespository extends CassandraTable[ZonePostRespository, Post] {

  object zone extends StringColumn(this) with PartitionKey[String]

  object date extends DateColumn(this) with PrimaryKey[Date]

  object linkhash extends StringColumn(this)

  object domain extends StringColumn(this)

  object title extends StringColumn(this)

  object article extends StringColumn(this)

  object metakeywords extends StringColumn(this)

  object metaDescription extends StringColumn(this)

  object link extends StringColumn(this)

  object imageUrl extends StringColumn(this)

  object seo extends StringColumn(this)

  object imagePath extends StringColumn(this)

  object caption extends StringColumn(this)

  object siteCode extends StringColumn(this)

  override def fromRow(row: Row): Post = {
    Post(
      zone(row),
      linkhash(row),
      domain(row),
      date(row),
      title(row),
      article(row),
      metakeywords(row),
      metaDescription(row),
      link(row),
      imageUrl(row),
      seo(row),
      imagePath(row),
      caption(row),
      siteCode(row)
    )
  }
}

object ZonePostRespository extends ZonePostRespository with DataConnection {
  override lazy val tableName = "zoneposts"

  def save(post: Post): Future[ResultSet] = {
    insert
      .value(_.linkhash, post.linkhash)
      .value(_.domain, post.domain)
      .value(_.date, post.date)
      .value(_.title, post.title)
      .value(_.article, post.article)
      .value(_.metakeywords, post.metakeywords)
      .value(_.metaDescription, post.metaDescription)
      .value(_.link, post.link)
      .value(_.zone, post.zone)
      .value(_.imageUrl, post.imageUrl)
      .value(_.seo, post.seo)
      .value(_.imagePath, post.imagePath)
      .value(_.caption, post.caption)
      .value(_.siteCode, post.siteCode)
      .future()
  }
  def getPostsByZone(zone: String) = {
    val today =DateTime.now.withTimeAtStartOfDay().toLocalDate.toDate
    select.where(_.zone eqs zone)
      .and(_.date gte today)
      .orderBy(_.date.desc)
      .fetchEnumerator() run Iteratee.collect()
  }
  def getZonePostsByDate(zone: String, date: Date): Future[Seq[Post]] = {
    select.where(_.zone eqs zone)
      .and(_.date gte date).orderBy(_.date.desc)
      .fetchEnumerator() run Iteratee.collect()
  }
  def getZonePostsByYesterday(zone: String, date: Date): Future[Seq[Post]] = {
    val today = new DateTime(date).plusDays(1).withTimeAtStartOfDay().toDate
    val yesterday = new DateTime(date).withTimeAtStartOfDay().toDate
    select.where(_.zone eqs zone)
      .and(_.date lt  today)
      .and(_.date gte yesterday)
      .orderBy(_.date.desc)
      .fetchEnumerator() run Iteratee.collect()
  }
  def getZoneCustomPosts(zone: String, start: Date, end: Date): Future[Seq[Post]] = {
    select.where(_.zone eqs zone)
      .and(_.date gte start)
      .and(_.date lt end)
      .orderBy(_.date.desc)
      .fetchEnumerator() run Iteratee.collect()
  }

}
