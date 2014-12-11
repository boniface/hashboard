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
      val feed1= CustomFeed("ZM","PT","121","http://postzambia.com/search.php?cmd=category&catid=67","www.test.com","site_Logo","news=")
      val feed2= CustomFeed("ZM","PT","122","http://postzambia.com/search.php?cmd=category&catid=3","www.test.com","site_Logo","news=")
      val feed3= CustomFeed("ZM","PT","123","http://postzambia.com/search.php?cmd=category&catid=70","www.test.com","site_Logo","news=")
      val feed4= CustomFeed("ZM","PT","124","http://postzambia.com/search.php?cmd=category&catid=7","www.test.com","site_Logo","news=")
      val feed5= CustomFeed("ZM","PT","125","http://postzambia.com/search.php?cmd=category&catid=6","www.test.com","site_Logo","news=")

      Then("The Values must validate ")
      rep.save(feed1)
      rep.save(feed2)
      rep.save(feed3)
      rep.save(feed4)
      rep.save(feed5)
      And("")
    }
  }

}
