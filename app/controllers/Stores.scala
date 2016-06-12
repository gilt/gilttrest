package controllers

import javax.inject._
import com.gilt.gilt.trest.v1.models._
import com.gilt.gilt.trest.v1.models.json._
import com.gilt.public.api.models.{Store => GiltStore}
import models.AuthenticatedAction
import org.joda.time.DateTime
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.PinService
import scala.concurrent.{ExecutionContext, Future}
import com.gilt.public.api.{Client => GiltClient}


@Singleton
class Stores @Inject()
( authAction: AuthenticatedAction,
  pinsService: PinService,
  giltClient: GiltClient,
  @Named("gilt-api-key") apiKey: String)
(implicit exec: ExecutionContext) extends Controller {

  def getByStore(store: Store) = authAction.async { request =>
    store match {
      case Store.Pinned =>
        getPinnedSales(request.user).map(sales => Ok(Json.toJson(sales)))
      case store if asGiltStore(store).isDefined =>
        getStoreSales(request.user, store).map(sales => Ok(Json.toJson(sales)))
      case _ =>
        Future.successful(badRequestWithError("Invalid Store"))
    }
  }

  private def getStoreSales(user: User, store: Store): Future[Seq[PinnedSale]] = {
    for {
      pins <- pinsService.find(user)
      saleList <- giltClient.saleList.getActiveJsonByStore(store.toString, apiKey)
    } yield {
      val saleKeySet = pins.map(_.saleKey).toSet[String]
      saleList.sales.sortBy(_.saleKey).map{ saleDetail =>
        PinnedSale(saleKeySet.contains(saleDetail.saleKey), saleDetail)
      }
    }
  }

  private def getPinnedSales(user: User): Future[Seq[PinnedSale]] = {
    for {
      pins <- pinsService.find(user)
      saleList <- giltClient.saleList.getActiveJson(apiKey)
    } yield {
      val saleKeySet = pins.map(_.saleKey).toSet[String]
      saleList.sales.collect{ case saleDetail if saleKeySet.contains(saleDetail.saleKey) => PinnedSale(true, saleDetail)}.sortBy(_.detail.saleKey)
    }
  }

  private def asGiltStore(store: Store): Option[GiltStore] = GiltStore.fromString(store.toString)

  private def badRequestWithError(msg: String): Result = BadRequest(Json.toJson(Error(msg)))

}
