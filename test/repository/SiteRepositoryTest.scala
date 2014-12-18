package repository

import domain.Site
import org.scalatest.{GivenWhenThen, FeatureSpec}
import respository.{WebSiteRepository, ZoneRespository, PostRespository, SiteRepository}
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
 * Created by hashcode on 2014/12/01.
 */
class SiteRepositoryTest  extends FeatureSpec with GivenWhenThen{
  feature("Test Crud Features for Site") {
    info("So that we Can use it to Persist Data")
    info("")

    scenario("Object is Created") {

      Given("An Object ")
      val sr = SiteRepository
      val post = PostRespository
      val zone = ZoneRespository
      val web =  WebSiteRepository





      When("Persisted in the Database   ")

      val res= Await.result(sr.createTable(), 2 minutes)


//      val resweb= Await.result(web.createTable(), 2 minutes)






      Then("The Values must validate ")
      val reszone= Await.result(zone.createTable(), 2 minutes)

      And(" And the Slices")

      And (" Again ")

      val respst= Await.result(post.createTable(), 2 minutes)


    }
  }

}
