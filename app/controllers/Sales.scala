package controllers

import javax.inject._
import models.AuthenticatedAction
import play.api._
import play.api.mvc._


import scala.concurrent.{ExecutionContext, Future, Promise}
import com.gilt.public.api.{Client => GiltClient}


@Singleton
class Sales @Inject()(authAction: AuthenticatedAction, giltApi: GiltClient)(implicit exec: ExecutionContext) extends Controller {


  def putBySaleKey(sale_key: String) = authAction.async { request =>
    Future.successful(NotImplemented)

  }

}
