package services

import javax.inject.{Inject, Singleton}
import com.gilt.gilt.trest.v1.models.User
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Failure, Success}


class PostgresUserService @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends UserService with UserSchema with HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  override def create(name: String, username: String, password: String)(implicit ec: ExecutionContext): Future[User] = {
    val result = db.run((users returning users.map(_.id) into ((user,id) => user.copy(id=id))) += UserRow(-1, name, username, password))
    result.map{ row => User(row.id, row.name, row.username) }
  }

  override def find(username: String, password: String)(implicit ec: ExecutionContext): Future[Option[User]] = {
    val result = db.run(users.filter { user => user.username === username.toLowerCase && user.password === password.toLowerCase }.result)
    result.map(_.headOption.map { row => User(row.id, row.name, row.username) })
  }


}

trait UserSchema { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  case class UserRow(id: Int, name: String, username: String, password: String)
  class UsersTable(tag: Tag) extends Table[UserRow](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def name = column[String]("name")
    def username = column[String]("username")
    def password = column[String]("password")

    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, name, username, password) <> (UserRow.tupled, UserRow.unapply)
  }

  lazy val users = TableQuery[UsersTable]

}
