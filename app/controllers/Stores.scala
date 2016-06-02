package controllers

import akka.actor.ActorSystem
import javax.inject._
import com.gilt.gilt.trest.v0.models.Store
import models.AuthenticatedAction
import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._


@Singleton
class Stores @Inject()(authAction: AuthenticatedAction)(implicit exec: ExecutionContext) extends Controller {

  def getByStore(store: Store) = authAction.async {
    Future(NotImplemented)
  }

}
