package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Users @Inject() (implicit exec: ExecutionContext) extends Controller {

  def postRegister() = Action.async {
    Future(NotImplemented)
  }

  def postLogin() = Action.async {
    Future(NotImplemented)
  }

}
