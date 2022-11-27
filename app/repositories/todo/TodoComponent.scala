package repositories.todo

import models.todo.Todo
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait TodoComponent extends HasDatabaseConfigProvider[JdbcProfile] with TodoListComponent {
  import profile.api._

  class TodoTable(tag: Tag) extends Table[Todo](tag, "todo") {

    def id          = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def description = column[String]("description")
    def isCompleted = column[Boolean]("is_completed")
    def todoListId  = column[Long]("todo_list_id")
    def todoList = foreignKey("todo_list_fk", todoListId, todoLists)(
      _.id,
      onUpdate = ForeignKeyAction.Restrict,
      onDelete = ForeignKeyAction.Cascade
    )

    override def * = (id, description, isCompleted, todoListId) <> ((Todo.apply _).tupled, Todo.unapply)
  }

  val todos = TableQuery[TodoTable]
}
