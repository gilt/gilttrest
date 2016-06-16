package controllers

import javax.inject._
import com.gilt.gilt.trest.v1.models.{LoginForm, Error, RegisterForm}
import com.gilt.gilt.trest.v1.models.json._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.UserService

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * NOTE: For simplicity, this is a VERY naive implementation of user registration and login
  * that stores passwords in plaintext.  This should never be used in any real production system!!!
  */

// Notice that UserService has been dependency injected into this controller.  But wait, UserService is just
// an interface!?!  Where is it implemented?  (Hint: Take a look at Module.scala)
@Singleton
class Users @Inject() (userService: UserService)(implicit exec: ExecutionContext) extends Controller {

  def postRegister() = Action.async(parse.json[RegisterForm]) { request =>
    val form: RegisterForm = request.body

    // TODO Return Ok with the user on success. BadRequest otherwise.

    // Remember, everything is async here, hence we are returning a Future.  You'll need to do the same.
    Future.successful(NotImplemented)

  }

  def postLogin() = Action.async(parse.json[LoginForm]) { request =>
    val form: LoginForm = request.body

    // TODO Return Ok with the user if found.  BadRequest otherwise.

    Future.successful(NotImplemented)

  }

  private def badRequestWithError(msg: String): Result = BadRequest(Json.toJson(Error(msg)))

}
