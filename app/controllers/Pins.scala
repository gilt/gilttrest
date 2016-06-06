package controllers

import javax.inject._
import com.gilt.gilt.trest.v0.models.Error
import com.gilt.gilt.trest.v0.models.json._
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
    val sale = giltClient.saleList.getActiveJson(apiKey).map{ saleDetail =>
      saleDetail.sales.find(_.saleKey == saleKey)
    }

    sale.flatMap{
      case Some(saleDetail) => pinsService.upsert(user, saleDetail).map(pin => Ok(Json.toJson(pin)))
      case None => Future.successful(badRequestWithError("Sale key not found"))
    }
  }

  def badRequestWithError(msg: String): Result = BadRequest(Json.toJson(Error(msg)))

}
