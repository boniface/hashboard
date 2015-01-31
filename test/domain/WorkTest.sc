import java.io.File
import java.net.URL

import com.gargoylesoftware.htmlunit.WebClient
import com.gravity.goose.{Configuration, Goose}
import com.rometools.rome.io.{SyndFeedInput, XmlReader}
import de.l3s.boilerpipe.BoilerpipeExtractor
import de.l3s.boilerpipe.extractors.CommonExtractors
import org.apache.commons.io.IOUtils
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.jsoup.Connection.Response
import org.jsoup.Jsoup
import respository.{CustomFeedRepository, CustomLinkRepository, CustomProcessedLinkskRepository}
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

// Custom Feeds Testing
val feed = "http://www.vanguardngr.com/feed/"
val ltfeed = "http://www.lusakatimes.com/feed/atom/"
val zwd = "http://www.zambianwatchdog.com/feed/"
val zr = "http://zambiareports.com/feed/"
val config = new Configuration
config.setBrowserUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36")
println(" the Browser is ",config.getBrowserUserAgent())
val goose = new Goose(config)
val article = goose.extractContent(feed)
//val article = goose.extractContent(url)

val in = IOUtils.toInputStream(article.getRawHtml(), "UTF-8")

val read = new XmlReader(in)

val fd = new SyndFeedInput().build(read).getEntries.asScala.toList

println("The Size is ",fd.size)
fd foreach(entry=>{
println("THE Link is ",entry.getLink)
})

