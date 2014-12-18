package respository

import java.util.Date

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.column.DateColumn
import com.websudos.phantom.iteratee.Iteratee
import conf.DataConnection
import domain.Post
import org.joda.time.DateTime

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/12/18.
 */
class WebSiteRepository extends CassandraTable[WebSiteRepository, Post] {

  object domain extends StringColumn(this) with PartitionKey[String]

  object date extends DateColumn(this) with PrimaryKey[Date] with ClusteringOrder[Date] with Descending

  object linkhash extends StringColumn(this) with PrimaryKey[String] with ClusteringOrder[String] with Descending

  object yeardate extends DateColumn(this)

  object zone extends StringColumn(this)

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
      yeardate(row),
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

object WebSiteRepository extends WebSiteRepository with DataConnection {
  override lazy val tableName = "websiteposts"

  def save(post: Post): Future[ResultSet] = {
    insert
      .value(_.linkhash, post.linkhash)
      .value(_.yeardate, post.yeardate)
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

  def getSitePosts(domain: String) = {
    select.where(_.domain eqs domain).orderBy(_.date.desc)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getSitePostsByDate(domain: String, date: Date): Future[Seq[Post]] = {
    select.where(_.domain eqs domain)
      .and(_.date gte date).orderBy(_.date.desc)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getSitePostsByYesterday(domain: String, date: Date): Future[Seq[Post]] = {
    val today = new DateTime(date).plusDays(1).withTimeAtStartOfDay().toDate
    val yesterday = new DateTime(date).withTimeAtStartOfDay().toDate
    select.where(_.domain eqs domain)
      .and(_.date lt today)
      .and(_.date gte yesterday)
      .orderBy(_.date.desc)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getSiteCustomPosts(domain: String, start: Date, end: Date): Future[Seq[Post]] = {
    select.where(_.domain eqs domain)
      .and(_.domain eqs domain)
      .and(_.date gte start)
      .and(_.date lt end).orderBy(_.date.desc)
      .fetchEnumerator() run Iteratee.collect()
  }

}
