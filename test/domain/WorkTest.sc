import java.net.URL

import de.l3s.boilerpipe.BoilerpipeExtractor
import de.l3s.boilerpipe.extractors.CommonExtractors
import org.jsoup.Jsoup
import respository.{CustomFeedRepository, CustomLinkRepository, CustomProcessedLinkskRepository}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

// Custom Feeds Testing
val feed = "https://www.daily-mail.co.zm/?feed=atom"







val result = Jsoup.connect(feed).get()

val dirtyLinks = result.select("a[href]")

val links = dirtyLinks.asScala.filter(link => link.attr("abs:href").contains("?p="))

links foreach( lks => {

  val link = lks.attr("abs:href").split("=")
  println("The Links are ", lks.attr("abs:href"))
})



//res
//
//val links = result.filter(link => link.attr("abs:href").contains("news.php"))
//links foreach(e=> e.)

