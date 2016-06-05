package controllers

import javax.inject._
import com.gilt.gilt.trest.v0.models.{LoginForm, Error, RegisterForm}
import com.gilt.gilt.trest.v0.models.json._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.UserService

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * NOTE: For simplicity, this is a VERY naive implementation of user registration and login
  * that stores passwords in plaintext.  This should never be used in any real production system!!!
  */
@Singleton
class Users @Inject() (userService: UserService)(implicit exec: ExecutionContext) extends Controller {

  def postRegister() = Action.async(parse.json[RegisterForm]) { request =>
    val username = request.body.username
    val name = request.body.name
    val password = request.body.password

    userService.create(name, username, password).map{ user =>
      Ok(Json.toJson(user))
    }
  }

  def postLogin() = Action.async(parse.json[LoginForm]) { request =>
    val username = request.body.username
    val password = request.body.password
    userService.find(username, password).map {
      case Some(user) => Ok(Json.toJson(user))
      case None => badRequestWithError("Login Failed")
    }

  }

  def badRequestWithError(msg: String): Result = BadRequest(Json.toJson(Error(msg)))

}
