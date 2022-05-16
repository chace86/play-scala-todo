package repositories.todo

import javax.inject.{Inject, Singleton}

import models.todo.Todo
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TodoRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class TodoTable(tag: Tag) extends Table[Todo](tag, "todo") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def description = column[String]("description")

    def isCompleted = column[Boolean]("is_completed")

    override def * = (id, description, isCompleted).mapTo[Todo]
  }

  private val todos = TableQuery[TodoTable]

  def getTodo(id: Long): Future[Option[Todo]] = db.run(todos.filter(_.id === id).result.headOption)
}
