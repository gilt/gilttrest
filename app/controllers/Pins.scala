package controllers

import javax.inject._
import com.gilt.gilt.trest.v1.models.Error
import com.gilt.gilt.trest.v1.models.json._
import com.gilt.public.api.{Client => GiltClient}
import models.AuthenticatedAction
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.PinService


import scala.concurrent.{ExecutionContext, Future}


@Singleton
class Pins @Inject()
( authAction: AuthenticatedAction,
  pinsService: PinService,
  giltClient: GiltClient,
  @Named("gilt-api-key") apiKey: String)(implicit exec: ExecutionContext) extends Controller
{


  def putBySaleKey(saleKey: String) = authAction.async { request =>
    val user = request.user

    // TODO: Determine if saleKey is found, and create/save the pin.  Return BadRequest if sale isn't found.

    Future.successful(NotImplemented)

  }

  def deleteBySaleKey(saleKey: String) = authAction.async { request =>
    val user = request.user

    //  TODO: Delete the pin with the provided saleKey and user

    Future.successful(NotImplemented)

  }

  private def badRequestWithError(msg: String): Result = BadRequest(Json.toJson(Error(msg)))

}
