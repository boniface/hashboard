package repository

import domain.Site
import org.scalatest.{GivenWhenThen, FeatureSpec}
import respository.SiteRepository
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by hashcode on 2014/12/01.
 */
class SiteRepositoryTest  extends FeatureSpec with GivenWhenThen{
  feature("Test Crud Features for Site") {
    info("So that we Can use it to Persist Data")
    info("")

    scenario("Object is Created") {

      Given("An Object ")
      val rep = SiteRepository
      rep.createTable()

      val site1 = Site("121","ZM","NyasaTimes","www.test.com","Zone Site","logo_url")
      val site2 = Site("122","ZM","NyasaTimes","www.test.com","Zone Site","logo_url")
      val site3 = Site("123","ZM","NyasaTimes","www.test.com","Zone Site","logo_url")
      val site4 = Site("124","ZM","NyasaTimes","www.test.com","Zone Site","logo_url")
      val site5 = Site("125","ZM","NyasaTimes","www.test.com","Zone Site","logo_url")
      val site6 = Site("126","MW","NyasaTimes","www.test.com","Zone Site","logo_url")
      val site7 = Site("127","MW","NyasaTimes","www.test.com","Zone Site","logo_url")
      val site8 = Site("128","MW","NyasaTimes","www.test.com","Zone Site","logo_url")
      val site9 = Site("129","MW","NyasaTimes","www.test.com","Zone Site","logo_url")
      val site10 = Site("1210","MW","NyasaTimes","www.test.com","Zone Site","logo_url")

      When("Persisted in the Database   ")
      val repo = SiteRepository
      repo.save(site1)
      repo.save(site2)
      repo.save(site3)
      repo.save(site4)
      repo.save(site5)
      repo.save(site6)
      repo.save(site7)
      repo.save(site8)
      repo.save(site9)
      repo.save(site10)

      Then("The Values must validate ")
      val sites = SiteRepository.getSitesByZone("ZM")
      sites map (s=> {
        println("The Size is ", s.size)
      })
      And(" And the Slices")

      val slices = SiteRepository.getSitesByNumber(6,2)
      slices map (list => {
        val v = list.toSeq
         v foreach( site => println("ID si :",site.id))
      })
    }
  }

}
