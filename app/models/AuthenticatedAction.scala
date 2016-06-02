package models

import javax.inject.{Singleton, Inject}

import org.apache.commons.codec.binary.Base64
import play.api.http.HeaderNames._
import play.api.libs.json.Json
import play.api.mvc._
import services.UserService

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
import com.gilt.gilt.trest.v0.models._
import com.gilt.gilt.trest.v0.models.json._

import play.api.mvc.{Request, WrappedRequest}


case class AuthenticatedRequest[A](user: User, request: Request[A]) extends WrappedRequest[A](request)

@Singleton
class AuthenticatedAction @Inject() (users: UserService) (implicit exec: ExecutionContext) extends ActionBuilder[AuthenticatedRequest] with Results {


  override def invokeBlock[A](request: Request[A], block: AuthenticatedRequest[A] => Future[Result]): Future[Result] = {
    request.headers.get(AUTHORIZATION).map{ authorizationHeader =>
      authorizationHeader.split(" ").toList match {
        case "Basic" :: creds :: Nil =>
          new String(Base64.decodeBase64(creds.getBytes)).split(":").toList match {
            case username :: password :: Nil =>
              users.getByUsernameAndPassword(username, password).flatMap {
                case Some(user) => block(AuthenticatedRequest(user, request))
                case _ => unauthorized
              }
            case _ => unauthorized
          }
        case _ => unauthorized
      }
    }.getOrElse(unauthorized)
  }

  private def unauthorized = Future.successful(Unauthorized(Json.toJson(Error("Authentication Failed"))))
}