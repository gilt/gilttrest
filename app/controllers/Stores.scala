package controllers

import javax.inject._
import com.gilt.gilt.trest.v0.models._
import com.gilt.gilt.trest.v0.models.json._
import models.AuthenticatedAction
import org.joda.time.DateTime
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import com.gilt.public.api.{Client => GiltClient}


@Singleton
class Stores @Inject()
  (authAction: AuthenticatedAction, giltClient: GiltClient, @Named("gilt-api-key") giltApiKey: String)
  (implicit exec: ExecutionContext) extends Controller
{

  def getByStore(store: Store) = authAction.async {
    store match {
      case Store.Pinned =>
        Future.successful(NotImplemented)
      case _ =>
        giltClient.SaleList.getActiveJsonByStore(store.toString, giltApiKey).map { saleList =>
          val sales = saleList.sales.map(x => Pin("asdf", DateTime.now))
          Ok(Json.toJson(sales))
        }
    }
  }

}
