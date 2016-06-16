package services


import java.sql.Timestamp
import javax.inject.Inject

import com.gilt.gilt.trest.v1.models.{Pin, User}
import com.gilt.public.api.models.SaleDetail
import org.joda.time.DateTime
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.{Future, ExecutionContext}


class PostgresPinService @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends PinService with PinSchema with UserSchema with HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  override def upsert(user: User, saleKey: String)(implicit ec: ExecutionContext): Future[Pin] = {

    val findAction = pins.filter(p => p.sale_key === saleKey && p.user_id === user.id).result.map(_.headOption)

    val updateAction = findAction.flatMap{
      case Some(sale) => DBIO.successful(sale)
      case None => (pins returning pins.map(_.id) into ((pin,id) => pin.copy(id=id))) += PinRow(-1, user.id, saleKey)
    }

    db.run(updateAction).map{ pin =>
      Pin(pin.saleKey)
    }

  }

  override def find(user: User)(implicit ec: ExecutionContext): Future[Seq[Pin]] = {
    val query = for{
      u <- users.filter(_.username === user.username)
      p <- pins.filter(_.user_id === u.id)
    } yield p

    db.run(query.result).map{ pins =>
      pins.map { pin =>
        Pin(pin.saleKey)
      }
    }

  }

  override def delete(user:User, saleKey: String)(implicit ec: ExecutionContext): Future[Int] = {
    val query = pins.filter(pin => pin.user_id === user.id && pin.sale_key === saleKey)
    db.run(query.delete)
  }

}

trait PinSchema { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  case class PinRow(id: Int, userId: Int, saleKey: String)
  class PinsTable(tag: Tag) extends Table[PinRow](tag, "pins") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def user_id = column[Int]("user_id")
    def sale_key = column[String]("sale_key")

    def * = (id, user_id, sale_key) <> (PinRow.tupled, PinRow.unapply)

  }

  lazy val pins = TableQuery[PinsTable]

}


