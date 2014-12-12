package services.posts

import java.util.{Date, UUID}

import com.gravity.goose.{Configuration, Goose}
import conf.Util
import domain.{ErrorReport, Post, CustomLink}
import respository.{ErrorReportRespository, PostRespository}

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/12.
 */
object FetchContentFromCustomLinks {
  def getContent(link:CustomLink) = {
    val article = Future{
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
          Util.getMetaKeywords(article.title),
          Util.getMedecription(article.getCleanedArticleText),
          article.getCanonicalLink,
          article.getTopImage().getImageSrc,
          Util.getPrettySeo(article.title),
          article.getTopImage().getImageSrc,
          Util.getCaption(),
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

}
