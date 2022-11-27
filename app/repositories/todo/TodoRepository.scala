package repositories.todo

import javax.inject.Inject
import javax.inject.Singleton

import models.todo.Todo
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class TodoRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends TodoComponent {

  import profile.api._

  // cannot insert auto-increment columns like id
  def create(description: String, isCompleted: Boolean, todoListId: Long): Future[Long] = db.run {
    todos
      // create projection of just description and completed status, since we are not inserting id
      .map(t => (t.description, t.isCompleted, t.todoListId))
      // define projection to return the id, to know what id is generated
      .returning(todos.map(_.id))
      // define transformation for the returned value, combines original params with new ones
      .into((data, id) => Todo(id, data._1, data._2, data._3)) += (description, isCompleted, todoListId)
  }.map(_.id) // return new id

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
              isCompleted.getOrElse(existing.isCompleted)
            )
        case None => DBIO.successful(0)
      }

    db.run(updateAction)
  }

  def findById(id: Long): Future[Option[Todo]] = db.run(todos.filter(_.id === id).result.headOption)

  // return number of rows deleted
  def delete(id: Long): Future[Int] = db.run(todos.filter(_.id === id).delete)
}
