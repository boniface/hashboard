package services.posts

import java.util.{Date, UUID}

import com.github.slugify.Slugify
import com.gravity.goose.{Configuration, Goose}
import domain.{ErrorReport, Link, Post}
import respository.{ErrorReportRespository, PostRespository}
import services.posts.filters.FilterService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Created by hashcode on 2014/12/11.
 */
object FetchContentFromRssLinks {

  def getContent(link: Link) = {
    val article = Future {
      new Goose(new Configuration).extractContent(link.url)
    }
    article onComplete {
      case Success(article) => {
        val post = Post(
          link.zone,
          link.linkhash,
          article.getDomain(),
          link.datePublished,
          article.getTitle(),
          article.getCleanedArticleText(),
          getMetaKeywords(article.title),
          getMedecription(article.getCleanedArticleText),
          article.getCanonicalLink,
          article.getTopImage().getImageSrc,
          getPrettySeo(article.title),
          article.getTopImage().getImageSrc,
          getCaption(),
          link.siteCode
        )
        PostRespository.save(post)
      }
      case Failure(error) => {
        val err = ErrorReport(link.zone, UUID.randomUUID().toString, link.site, new Date, "GOOSE: EXTRACTION ERROR: " + error.getMessage)
        ErrorReportRespository.save(err)
      }
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
