package repositories.todo

import javax.inject.Inject
import javax.inject.Singleton

import models.todo.Todo
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.PostgresProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class TodoRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[PostgresProfile]
    with TodoComponent
    with TodoListComponent {

  import profile.api._

  // auto-increment id column is ignored
  def create(todo: Todo): Future[Long] =
    db.run(
      todos
        .map(t => (t.description, t.todoListId, t.isCompleted))
        .returning(todos.map(_.id)) += (todo.description, todo.todoListId, todo.isCompleted.getOrElse(false))
    )

  // return number of rows updated
  def update(id: Long, description: Option[String], isCompleted: Option[Boolean]): Future[Int] = {
    // default to existing values in the case that description or isCompleted is None
    val updateAction = todos
      .filter(_.id === id)
      .result.headOption
      .flatMap {
        case Some(existing) =>
          todos
            .filter(_.id === id)
            .map(t => (t.description, t.isCompleted))
            .update(
              description.getOrElse(existing.description),
              isCompleted
                .orElse(existing.isCompleted)
                .getOrElse(false)
            )
        case None => DBIO.successful(0)
      }

    db.run(updateAction)
  }

  def findById(id: Long): Future[Option[Todo]] = db.run(todos.filter(_.id === id).result.headOption)

  // return number of rows deleted
  def delete(id: Long): Future[Int] = db.run(todos.filter(_.id === id).delete)
}
