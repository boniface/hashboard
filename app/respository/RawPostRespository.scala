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
import com.websudos.phantom.keys.PrimaryKey
import conf.DataConnection
import domain.RawPost

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/07/13.
 */
class RawPostRespository extends CassandraTable[RawPostRespository, RawPost] {

  object linkhash extends StringColumn(this) with PartitionKey[String]

  object zone extends StringColumn(this) with PrimaryKey[String]
  object datePublished extends DateColumn(this) with PrimaryKey[Date]

  object rawHtml extends StringColumn(this)


  override def fromRow(row: Row): RawPost = {
    RawPost(
      zone(row),
      linkhash(row),
      datePublished(row),
      rawHtml(row))
  }
}

object RawPostRespository extends RawPostRespository with DataConnection {
  override lazy val tableName = "rawposts"

  def save(rawPost: RawPost): Future[ResultSet] = {
    insert
      .value(_.linkhash, rawPost.linkhash)
      .value(_.zone, rawPost.zone)
      .value(_.datePublished, rawPost.datePublished)
      .value(_.rawHtml, rawPost.rawHtml)
      .future()
  }

  def getRawById(linkhash: String): Future[Option[RawPost]] = {
    select.where(_.linkhash eqs linkhash).one();
  }
}
