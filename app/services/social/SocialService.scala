package services.social

import domain.Social
import respository.SocialRepository
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2015/02/10.
 */

object SocialService {

  def save(social: Social) = {
    SocialRepository.save(social)
  }

  def getSocialService(service: String) = {
    SocialRepository.getSocialService(service)

  }

  def saveProps(service: String, props: Map[String, String]) = {
    val result = getSocialService(service)
    result map (social => {
      social match {
        case Some(original) => {
          val updatedSocial = original.copy(props + props)
          SocialRepository.save(updatedSocial)
        }
        case None => None
      }
    })
  }

  def removeProperty(service: String, key: String) = {
    val result = getSocialService(service)
    result map (social => {
      social match {
        case Some(original) => {
          val updatedSocial = original.copy(props - props)
          SocialRepository.save(updatedSocial)
        }
        case None => None
      }
    })

  }

  def removeProps(service: String) = {
    SocialRepository.deleteSocial(service)
  }
}
