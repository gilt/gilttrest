package services


import com.gilt.gilt.trest.v1.models.User


import scala.concurrent.{Future, ExecutionContext}


trait UserService {

  def create(name: String, username: String, password: String)(implicit ec: ExecutionContext): Future[User]

  def find(username: String, password: String)(implicit ec: ExecutionContext): Future[Option[User]]


}
