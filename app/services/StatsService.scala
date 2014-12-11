package services

import domain.Stats
import respository.StatsRepository

/**
 * Created by hashcode on 2014/12/01.
 */
object StatsService {

  def statIncrement(stat:Stats) = {
    StatsRepository.statIncrement(stat)
  }

  def statDecrement(stat:Stats) = {
    StatsRepository.statDecrement(stat)

  }

  def getStats(item:String,subjectId:String) = {
    StatsRepository.getStats(item,subjectId)
  }


}
