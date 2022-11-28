package repositories.todo

import models.todo.TodoList
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.PostgresProfile

trait TodoListComponent { self: HasDatabaseConfigProvider[PostgresProfile] =>
  import profile.api._

  class TodoListTable(tag: Tag) extends Table[TodoList](tag, "todo_list") {

    def id    = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("description")

    override def * = (id, title) <> ((TodoList.apply _).tupled, TodoList.unapply)
  }

  val todoLists = TableQuery[TodoListTable]
}
