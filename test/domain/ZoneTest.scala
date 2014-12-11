package domain

import org.scalatest.{GivenWhenThen, FeatureSpec}

/**
 * Created by hashcode on 2014/12/01.
 */
class ZoneTest extends FeatureSpec with GivenWhenThen {
  feature("Create a Custom Feed Object") {
    info("So that we Can use it to Persist Data")
    info("")
    scenario("Object is Created") {
      Given("Zone Object ")

      When("Created ")

      val zone = Zone(
        code = "ZM",
        name = "Zambia",
        status = "Active",
        flag = "flag_url"
      )

      Then("The Values must validate ")

        assert(zone.name === "Zambia")
        assert(zone.code === "ZM")
        assert(zone.status === "Active")
        assert(zone.flag === "flag_url")
    }

  }

}
