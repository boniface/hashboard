package services

import org.joda.time.DateTime
import respository.ErrorReportRespository
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/13.
 */
object ErrorReportsService {
  val today = DateTime.now.withTimeAtStartOfDay().toLocalDate.toDate
  def getErrorreports(zone:String)={
   ErrorReportRespository.getErrorReport(zone) map (posts => posts.filter(_.date.after(today)))
  }

}
