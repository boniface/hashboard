package domain

import org.scalatest.{GivenWhenThen, FeatureSpec}

/**
 * Created by hashcode on 2014/12/01.
 */
class CustomFeedTest extends  FeatureSpec with GivenWhenThen{
  feature("Create a Custom Feed Object"){
    info("So that we Can use it to Persist Data")
    info("")
    scenario("Object is Created"){
      Given("CustomFeed Object ")

      When("Created  ")
      val feed = CustomFeed(
        zone="ZM",
        siteCode="LT",
        id="123",
        feedLink="www.site.com/news",
        feedSite="www.site.com",
        siteLogo="logo_url",
        filter="newsFilter"
      )
      Then("The Values must validate ")
        assert(feed.zone=="ZM")
        assert(feed.siteCode=="LT")
        assert(feed.id=="123")
        assert(feed.feedLink=="www.site.com/news")
        assert(feed.feedSite=="www.site.com")
        assert(feed.siteLogo=="logo_url")
        assert(feed.filter=="newsFilter")
      And("")
    }
  }

}
