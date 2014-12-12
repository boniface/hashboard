package services.posts.filters

import java.util.UUID

import conf.Util
import respository.FilterExpressionRepository

/**
 * Created by hashcode on 2014/09/21.
 */
object FilterService {
  def removeStopWords(string: String):String = {
    val sets = string.split(" ").toSet
    val filter = new FilterExpressionRepository
    filter.removeStopWords(sets,"KEYN"+key)
  }
  def key=Util.md5Hash(UUID.randomUUID().toString)

}
