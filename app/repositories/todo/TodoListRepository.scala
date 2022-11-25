package repositories.todo

import javax.inject.Inject
import javax.inject.Singleton

import models.todo.TodoList
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

@Singleton
class TodoListRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit
    ec: ExecutionContext
) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private class TodoListTable(tag: Tag) extends Table[TodoList](tag, "todo_list") {

    def id    = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("description")

    override def * = (id, title) <> ((TodoList.apply _).tupled, TodoList.unapply)
  }

  private val todoLists = TableQuery[TodoListTable]

  def create(title: String): Future[Long] = db.run {
    todoLists
      .map(_.title)
      .returning(todoLists.map(_.id))
      .into((data, id) => TodoList(id, data)) += title
  }.map(_.id)

}
