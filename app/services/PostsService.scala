package services

import org.joda.time.DateTime
import respository.PostRespository
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/13.
 */
object PostsService {
  val today = DateTime.now.withTimeAtStartOfDay().toLocalDate.toDate

  def getTodayZonePosts(zone:String) = {
    PostRespository.getLatestPosts(zone) map (posts => posts.filter(_.date.after(today)))
  }

}
