package controllers

import play.api.libs.json.Json
import play.api.mvc._
import respository._

import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Hello Your new application is ready."))
  }

  def options(path: String) = Action {
    Ok("")
  }

  def dbsetup = Action.async {
    val results = for {
      feed <- FeedsRespository.createTable()
      site <- SiteRepository.createTable()
      zone <- ZoneRespository.createTable()
      stats <- StatsRepository.createTable()
      customFeed <- CustomFeedRepository.createTable()
      link <- LinksRespository.createTable()
      ereport <- ErrorReportRespository.createTable()
      clinks <- CustomLinkRepository.createTable()
      posts <- PostRespository.createTable()
      sposts <- SitePostRespository.createTable()
      zposts <- ZonePostRespository.createTable()
      web <- WebSiteRepository.createTable()
    } yield (site)
    results map (result => {
      Ok(Json.toJson("Done"))
    })
  }

  def home = AuthenticatedAction { request =>
    Ok("Authenticated response...")
  }

  def authenticate(request: Request[AnyContent]) = false

  def AuthenticatedAction(f: Request[AnyContent] => Result): Action[AnyContent] = Action { request =>
    if (authenticate(request)) {
      f(request)

    }
    else {
      Unauthorized("Invalid user name or password")

    }
  }


}