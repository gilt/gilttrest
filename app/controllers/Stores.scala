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

    //TODO:  Construct a list of PinnedSales for this user and store
    //TODO:  Sort the resulting list by sale key
    //HINT:  You will need to merge the results of the API (GiltClient) call with the PinService (DB) call

    Future.successful(Seq.empty[PinnedSale])
  }

  private def getPinnedSales(user: User): Future[Seq[PinnedSale]] = {
    //TODO:  Construct a list of PinnedSales for this user across all stores filters by PinnedSale.pinned = true
    Future.successful(Seq.empty[PinnedSale])
  }

  private def asGiltStore(store: Store): Option[GiltStore] = GiltStore.fromString(store.toString)

  private def badRequestWithError(msg: String): Result = BadRequest(Json.toJson(Error(msg)))

}
