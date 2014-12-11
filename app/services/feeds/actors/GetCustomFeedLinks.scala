package services.feeds.actors

import java.util.{UUID, Date}

import conf.Util
import domain.{ErrorReport, CustomFeed, CustomLink}
import org.jsoup.Jsoup
import respository.{CustomFeedRepository, CustomLinkRepository, CustomProcessedLinkskRepository}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

/**
 * Created by hashcode on 2014/12/09.
 */
object GetCustomFeedLinks {

  def processCustomFeeds(zone: String) = {
    val customFeeds = CustomFeedRepository.getFeedsByZone(zone)
    customFeeds map (customFeedsLinks =>{
      customFeedsLinks foreach(customFeed =>getLinksFromCustomFeeds(customFeed))
    })
  }

  private def getLinksFromCustomFeeds(customFeed:CustomFeed) = {
    val plinks = scala.collection.mutable.MutableList[Clink]()

    Try(Jsoup.connect(customFeed.feedLink).get().select("a[href]").asScala
      .filter(link => link.attr("abs:href").contains(customFeed.filter))) match {
      case Success(result) => result foreach (link => {
        val r = link.attr("abs:href").split("=")
        if (r.size > 1) {
          plinks.+=(Clink(Util.getIntFromString(r(1)), link.attr("abs:href"), link.text(), customFeed.zone))
        }
      })
      case Failure(ex) => {
        val error = ErrorReport(customFeed.zone,UUID.randomUUID().toString,customFeed.feedLink,new Date,"CUSTOM FEED ERROR: "+ex.getMessage)
        scala.collection.mutable.MutableList[Clink]()
      }
    }
    val filteredLinks = filterLinks(plinks)

    postLinks(filteredLinks, customFeed)
  }

  private def filterLinks(links: scala.collection.mutable.MutableList[Clink]) = {
    links.filter(link => link.title.length > 12).sortBy(link => link.id).reverse take (20)
  }

  private def postLinks(links: scala.collection.mutable.MutableList[Clink], feed: CustomFeed) = {
    links foreach (link => {
      val results = CustomProcessedLinkskRepository.getLinkById(Util.md5Hash(link.url))
      results map (result => {
        result match {
          case Some(clink) => None
          case None => {
            val value = CustomLink(
              link.zone,
              new Date(),
              Util.md5Hash(link.url),
              link.url,
              feed.feedSite,
              feed.siteCode,
              link.title)
            CustomLinkRepository.save(value)
          }
        }
      })
    })
  }

}
