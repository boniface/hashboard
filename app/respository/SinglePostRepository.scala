package respository

import com.datastax.driver.core.{ResultSet, Row}
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits._
import com.websudos.phantom.column.DateColumn
import conf.DataConnection
import domain.Post

import scala.concurrent.Future

/**
 * Created by hashcode on 2014/12/20.
 */
class SinglePostRepository extends CassandraTable[SinglePostRepository, Post] {

  object linkhash extends StringColumn(this) with PartitionKey[String]

  object domain extends StringColumn(this)

  object date extends DateColumn(this)

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

object SinglePostRepository extends SinglePostRepository with DataConnection {
  override lazy val tableName = "singleposts"

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

}
