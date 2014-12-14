package repository

import java.util.Date

import domain.ErrorReport
import org.scalatest.{GivenWhenThen, FeatureSpec}
import respository.ErrorReportRespository

/**
 * Created by hashcode on 2014/12/09.
 */
class ErrorreportTest extends FeatureSpec with GivenWhenThen{
  feature("Create a Custom Feed Object") {
    info("So that we Can use it to Persist Data")
    info("")
    scenario("Object is Created") {
      Given("CustomFeed Object ")
      val error = ErrorReport("ZM","1","test.com",new Date,"ERROR")
      When("Created  ")
      ErrorReportRespository.save(error)
      Then("The Values must validate ")
      val report = ErrorReportRespository.getErrorReport("ZM")


      And("")
    }
  }

}
