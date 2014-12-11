package domain


import org.scalatest.{GivenWhenThen, FeatureSpec}


/**
 * Created by hashcode on 2014/12/01.
 */

class FeedTest extends FeatureSpec with GivenWhenThen {

  feature(" Create Feed Domain Class") {

    info(" As A Dev I want to Create an Object")
    info(" So that I can see the use it for Data Collection")
    info("")
    scenario("Create am Instance of the Object  ") {

      Given(" A Feed Object ")

      When(" Object is Created")

      val feed = Feed(
        zone = "ZM",
        id = "123",
        feedLink = "www.test.com/feed",
        feedType = "rss",
        feedSite = "www.test.com",
        siteLogo = "logo url",
        siteCode = "LT")

      Then("The Fields Should be assert correctly ")
        assert(feed.zone === "ZM")
        assert(feed.id === "123")
        assert(feed.feedLink === "www.test.com/feed")
        assert(feed.feedType === "rss")
        assert(feed.feedSite === "www.test.com")
        assert(feed.siteLogo === "logo url")
        assert(feed.siteCode === "LT")
    }

  }

}
