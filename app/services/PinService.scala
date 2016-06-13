package services

import com.gilt.gilt.trest.v1.models.{Pin, User}
import com.gilt.public.api.models.SaleDetail
import org.joda.time.DateTime

import scala.concurrent.{Future, ExecutionContext}

/**
  * Created by kli on 6/5/16.
  */
trait PinService {

  def upsert(user: User, sale: String, date: DateTime)(implicit ec: ExecutionContext): Future[Pin]

  def find(user: User, date: DateTime = DateTime.now)(implicit ec: ExecutionContext): Future[Seq[Pin]]

  def delete(user: User, saleKey: String)(implicit ec: ExecutionContext): Future[Int]

}
