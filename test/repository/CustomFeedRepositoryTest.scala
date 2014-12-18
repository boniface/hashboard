package repository

import domain.CustomFeed
import org.scalatest.{GivenWhenThen, FeatureSpec}
import respository.CustomFeedRepository

/**
 * Created by hashcode on 2014/12/04.
 */
class CustomFeedRepositoryTest extends FeatureSpec with GivenWhenThen{
  feature("Create a Custom Feed") {
    info("So that we Can use it to Persist Data")
    info("")
    scenario("Create an Object to be persisted") {

      Given("CustomFeed Object ")
      val rep = CustomFeedRepository
      When("Created  ")

      Then("The Values must validate ")

      And("")
    }
  }

}
