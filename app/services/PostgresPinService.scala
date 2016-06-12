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

  override def upsert(user: User, sale: SaleDetail)(implicit ec: ExecutionContext): Future[Pin] = {
    val expiresAt  = asTimestamp(sale.ends.get)

    val findAction = pins.filter(p => p.sale_key === sale.saleKey && p.user_id === user.id).result.map(_.headOption)

    val updateAction = findAction.flatMap{
      case Some(sale) => DBIO.successful(sale)
      case None => (pins returning pins.map(_.id) into ((pin,id) => pin.copy(id=id))) += PinRow(-1, user.id, sale.saleKey, expiresAt)
    }

    db.run(updateAction).map{ pin =>
      Pin(pin.saleKey, asDateTime(pin.expiresAt))
    }

  }

  override def find(user: User, date: DateTime = DateTime.now)(implicit ec: ExecutionContext): Future[Seq[Pin]] = {
    val query = for{
      u <- users.filter(_.username === user.username)
      p <- pins.filter(_.user_id === u.id)
    } yield p

    db.run(query.result).map{ pins =>
      pins.map { pin =>
        val expiresAt = new DateTime(pin.expiresAt)
        Pin(pin.saleKey, expiresAt)
      }
    }

  }

  override def delete(user:User, saleKey: String)(implicit ec: ExecutionContext): Future[Int] = {
    val query = pins.filter(pin => pin.user_id === user.id && pin.sale_key === saleKey)
    db.run(query.delete)
  }


  def asTimestamp(date: DateTime):Timestamp = new Timestamp(date.getMillis)
  def asDateTime(date: Timestamp): DateTime = new DateTime(date)
}

trait PinSchema { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  case class PinRow(id: Int, userId: Int, saleKey: String, expiresAt: Timestamp)
  class PinsTable(tag: Tag) extends Table[PinRow](tag, "pins") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def user_id = column[Int]("user_id")
    def sale_key = column[String]("sale_key")
    def expires_at = column[Timestamp]("expires_at")

    def * = (id, user_id, sale_key, expires_at) <> (PinRow.tupled, PinRow.unapply)

  }

  lazy val pins = TableQuery[PinsTable]

}


