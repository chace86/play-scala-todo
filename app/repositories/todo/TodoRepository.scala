package repositories.todo

import javax.inject.{Inject, Singleton}

import models.todo.Todo
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TodoRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private class TodoTable(tag: Tag) extends Table[Todo](tag, "todos") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def description = column[String]("description")
    def isCompleted = column[Boolean]("is_completed")

    override def * = (id, description, isCompleted) <> ((Todo.apply _).tupled, Todo.unapply)
  }

  private val todos = TableQuery[TodoTable]

  def findById(id: Long): Future[Option[Todo]] = db.run(todos.filter(_.id === id).result.headOption)
}
