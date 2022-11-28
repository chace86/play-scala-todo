package repositories.todo

import models.todo.Todo
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.PostgresProfile

trait TodoComponent { self: HasDatabaseConfigProvider[PostgresProfile] with TodoListComponent =>
  import profile.api._

  class TodoTable(tag: Tag) extends Table[Todo](tag, "todo") {

    def id          = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def description = column[String]("description")
    def todoListId  = column[Long]("todo_list_id")
    def isCompleted = column[Boolean]("is_completed", O.Default(false))

    override def * = (id.?, description, todoListId, isCompleted.?) <> ((Todo.apply _).tupled, Todo.unapply)

    def todoListFk = foreignKey("todo_list_fk", todoListId, todoLists)(
      _.id,
      onUpdate = ForeignKeyAction.Restrict,
      onDelete = ForeignKeyAction.Cascade
    )
  }

  val todos = TableQuery[TodoTable]
}
