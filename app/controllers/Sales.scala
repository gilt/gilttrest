package controllers

import javax.inject._
import models.AuthenticatedAction
import play.api._
import play.api.mvc._

import services.Counter
import scala.concurrent.{ExecutionContext, Future, Promise}

/**
 * This controller demonstrates how to use dependency injection to
 * bind a component into a controller class. The class creates an
 * `Action` that shows an incrementing count to users. The [[Counter]]
 * object is injected by the Guice dependency injection system.
 */
@Singleton
class Sales @Inject()(authAction: AuthenticatedAction)(implicit exec: ExecutionContext) extends Controller {


  def putBySaleKey(sale_key: String) = authAction.async { request =>
    Future.successful(NotImplemented)

  }

}
