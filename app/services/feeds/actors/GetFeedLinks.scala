package services.feeds.actors

import java.net.URL
import java.util.{Date, UUID}

import com.gravity.goose.{Goose, Configuration}
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.{SyndFeedInput, XmlReader}
import conf.Util
import domain.{ErrorReport, Feed, Link}
import org.apache.commons.io.IOUtils
import respository.{LinksRespository, ErrorReportRespository, FeedsRespository}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Created by hashcode on 2014/12/09.
 */
object GetFeedLinks {

  def processFeed(code: String) = {
    val feeds = FeedsRespository.getFeedsByZone(code)
    feeds map (feedLinks => {
      feedLinks foreach (feed => postLinks(feed))
    })
  }


  private def postLinks(feed: Feed) = {
    val f: Future[List[SyndEntry]] = Future {

      val config = new Configuration
      config.setBrowserUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36")
      val goose = new Goose(config)
      val article = goose.extractContent(feed.feedLink)

      val inputStream = IOUtils.toInputStream(article.getRawHtml(), "UTF-8")

      val read = new XmlReader(inputStream)
      new SyndFeedInput().build(read).getEntries.asScala.toList

    }
    f onComplete {
      case Success(links) => links foreach (entry => {
        val link = Link(feed.zone,
          Util.md5Hash(entry.getUri),
          entry.getPublishedDate,
          entry.getUri,
          feed.feedSite,
          feed.siteCode,
          entry.getTitle, feed.feedType)
//        println("zone: ", link.zone, " Date: ", link.datePublished, "Site: ", link.site, "TITLE: ", link.title)
        LinksRespository.save(link)
      })
      case Failure(t) => {
        val error = ErrorReport(feed.zone, UUID.randomUUID().toString, feed.feedSite, new Date, "FEED ERROR: " + t.getMessage)
        ErrorReportRespository.save(error)

      }
    }
  }
}

