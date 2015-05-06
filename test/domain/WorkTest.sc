import java.io.File
import java.net.URL
import java.util.{Date, UUID}
import com.gargoylesoftware.htmlunit.WebClient
import com.gravity.goose.{Configuration, Goose}
import com.rometools.rome.io.{SyndFeedInput, XmlReader}
import conf.Util
import de.l3s.boilerpipe.BoilerpipeExtractor
import de.l3s.boilerpipe.extractors.CommonExtractors
import domain.ErrorReport
import org.apache.commons.io.IOUtils
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.jsoup.Connection.Response
import org.jsoup.Jsoup
import respository.{CustomFeedRepository, CustomLinkRepository, CustomProcessedLinkskRepository}
import services.feeds.actors.Clink
import twitter4j.{Query, TwitterFactory}
import twitter4j.conf.ConfigurationBuilder
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}
// Custom Feeds Testing
val feed = "http://www.botswanaguardian.co.bw/news.html"
val ltfeed = "http://www.lusakatimes.com/feed/atom/"
val zwd = "http://www.zambianwatchdog.com/feed/"
val zr = "http://zambiareports.com/feed/"
private def getLinksFromCustomFeeds(feed:String) = {
  val plinks = scala.collection.mutable.MutableList[Clink]()

  val links = List[String]()

  Try(Jsoup.connect(feed)
    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36")
    .referrer("https://www.google.com")
    .get()
    .select("a[href]")
    .asScala
    .filter(link => link.attr("abs:href")
    .contains("news/"))) match {
    case Success(result) => result foreach (link => {
      val r = link.attr("abs:href")

      println("The Link is ", links :+ r)
//      if (r.size > 1) {
//        plinks.+=(Clink(Util.getIntFromString(r(1)), link.attr("abs:href"), link.text(), "ZM"))
//      }
      links
    })
    case Failure(ex) => {
      println("There is a Error!!!!!!!")
      val error = ErrorReport("ZM", UUID.randomUUID().toString, feed, new Date, "CUSTOM FEED ERROR: " + ex.getMessage)
      scala.collection.mutable.MutableList[Clink]()
    }
  }




//  val filteredLinks = filterLinks(plinks)
//
//  filteredLinks
}
val feds = getLinksFromCustomFeeds(feed)
//println(" THE FEED SIZE IS ",feds.size)
//private def filterLinks(links: scala.collection.mutable.MutableList[Clink]) = {
//  links.filter(link => link.title.length > 12).sortBy(link => link.id).reverse take (20)
//}
val  cb = new ConfigurationBuilder()
cb.setDebugEnabled(true)
  .setOAuthConsumerKey("GmIKKGsaecNNOeAiJh8xf8cup")
  .setOAuthConsumerSecret("RTb3xQ5kwRqpn1Yij04jlWnUtmrAe69YOHYq0x8aUWanO7kQ2Y")
  .setOAuthAccessToken("2994411497-dA79vLUbrzvrH5Uuucvcz4RgLUgnvbcLROm2ESb")
  .setOAuthAccessTokenSecret("p1EOUaeieJ6oyM5VXJFvwMegJs9acUCd491QRbPJ3Al4N")
val  tf = new TwitterFactory(cb.build())
val twitter =tf.getInstance()
val message="All Zambian News from All Major Online Sites http://www.zambiahash.com #Zambia"

twitter.updateStatus("Hello , This is a sample tweet")
val statuses = twitter.getHomeTimeline