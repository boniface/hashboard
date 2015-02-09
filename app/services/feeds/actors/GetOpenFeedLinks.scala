package services.feeds.actors

import java.util.{UUID, Date}

import conf.Util
import domain.{ErrorReport, CustomLink, OpenFeed}
import org.jsoup.Jsoup
import respository.{ErrorReportRespository, CustomLinkRepository, OpenFeedRepository}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

/**
 * Created by hashcode on 2015/02/06.
 */
object GetOpenFeedLinks {
  def processOpenFeeds(zone: String) = {
    val openFeedsLinks = OpenFeedRepository.getFeedsByZone(zone)
    openFeedsLinks map (feeds => {
      feeds foreach (feed => postOpenFeedLinks(feed))
    })
  }

  def postOpenFeedLinks(feed: OpenFeed) = {
    Try(Jsoup.connect(feed.feedLink)
      .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36")
      .referrer("https://www.google.com")
      .get()
      .select("a[href]")
      .asScala
      .filter(link => link.attr("abs:href")
      .contains(feed.filter))) match {
      case Success(result) => {
        result foreach (link => {
          val value = CustomLink(
            feed.zone,
            new Date(),
            Util.md5Hash(link.attr("abs:href")),
            link.attr("abs:href"),
            feed.feedSite,
            feed.siteCode,
            link.text())
          CustomLinkRepository.save(value)
        })
      }
      case Failure(ex) => {
        val error = ErrorReport(feed.zone, UUID.randomUUID().toString, feed.feedSite, new Date, "OPEN FEED ERROR: " + ex.getMessage)
        ErrorReportRespository.save(error)
      }
    }
  }
}
