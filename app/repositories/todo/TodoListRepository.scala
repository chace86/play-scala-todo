package repositories.todo

import javax.inject.Inject
import javax.inject.Singleton

import models.todo.TodoList
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.PostgresProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class TodoListRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit
    ec: ExecutionContext
) extends HasDatabaseConfigProvider[PostgresProfile]
    with TodoListComponent {

  import profile.api._

  def create(title: String): Future[Long] =
    db.run(
      todoLists
        .map(_.title)
        .returning(todoLists.map(_.id)) += title
    )

  def update(id: Long, title: String): Future[Int] =
    db.run(
      todoLists
        .filter(_.id === id)
        .map(_.title)
        .update(title)
    )

  def findById(id: Long): Future[Option[TodoList]] =
    db.run(
      todoLists
        .filter(_.id === id)
        .result.headOption
    )

  def delete(id: Long): Future[Int] =
    db.run(
      todoLists
        .filter(_.id === id)
        .delete
    )
}
