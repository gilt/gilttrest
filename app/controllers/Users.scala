package controllers

import javax.inject._
import com.gilt.gilt.trest.v0.models.RegisterForm
import com.gilt.gilt.trest.v0.models.json._
import play.api._
import play.api.mvc._
import services.UserService

import scala.concurrent.{ExecutionContext, Future, Promise}


@Singleton
class Users @Inject() (userService: UserService)(implicit exec: ExecutionContext) extends Controller {

  /**
    * Note: This is a very naive implementation of user registration and login
    * that stores passwords at rest in plaintext. A big no no in production systems.
    * @return
    */
  def postRegister() = Action.async(parse.json[RegisterForm]) { request =>
    val username = request.body.username
    val name = request.body.name
    val password = request.body.password

    Future(NotImplemented)
  }

  def postLogin() = Action.async {
    Future(NotImplemented)
  }

}
