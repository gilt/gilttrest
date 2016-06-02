package services

import javax.inject.Singleton

import com.gilt.gilt.trest.v0.models.User

import scala.concurrent.{Future, ExecutionContext}

@Singleton
class UserService {

  def getByUsernameAndPassword(username: String, password: String)(implicit ec: ExecutionContext): Future[Option[User]] = {
    Future.successful(Some(User("foo", "bar")))
  }

}
