package conf

import java.util.Date

import com.github.slugify.Slugify
import org.joda.time.{DateTimeConstants, DateTime}
import services.posts.filters.FilterService

import scala.util.{Failure, Success, Try}

/**
 * Created by hashcode on 2014/12/01.
 */
object Util extends Enumeration {

  import java.net._
  def md5Hash(text: String): String = {
    val hash = text + InetAddress.getLocalHost.getHostName
    java.security.MessageDigest.getInstance("MD5").digest(hash.getBytes()).map(0xFF & _).map {
      "%02x".format(_)
    }.foldLeft("") {
      _ + _
    }
  }

  val SPAM, PENDING, APPROVED, QUESTION, RESPONSE, SMFEED,FEED, POST, ENABLED, DISABLED, TODAY,YESTERDAY,WEEK,MONTH = Value

  def getDate(date: String): Date = {
    date match{
      case "TODAY" =>  DateTime.now.withTimeAtStartOfDay().toLocalDate.toDate
      case "YESTERDAY"=>  DateTime.now.minusDays(1).toDate
      case "WEEK" =>  DateTime.now.withTimeAtStartOfDay.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(7).toDate
      case "MONTH" =>  DateTime.now.withTimeAtStartOfDay.dayOfMonth.withMinimumValue.toDate
      case _ => DateTime.now.toDate
    }
  }

  def getDateFromString(date:String):Date ={
    DateTime.parse(date).toDate
  }
  def getIntFromString(value:String):Int ={
    Try(Integer.parseInt(value)) match {
      case Success(ans) => ans
      case Failure(ex) => 0
    }
  }

  def getMetaKeywords(title: String) = {
    val cleanedWords = FilterService.removeStopWords(title)
    cleanedWords.split(' ').map(_.capitalize).mkString(",")
  }

  def getMedecription(article: String) = {
    if (article.length > 156) {
      val description = article.substring(0, 156)
      description.split(' ').map(_.capitalize).mkString(" ")
    } else {
      article
    }
  }

  def getPrettySeo(title: String) = {
    val cleanedWords = FilterService.removeStopWords(title)
    val slg = new Slugify()
    slg.slugify(cleanedWords)
  }

  def getCaption() = {
    "NoCaption"
  }

  def getMovies = {

  }
}
