package controllers.todo

import javax.inject._

import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._
import repositories.todo.TodoRepository

import scala.concurrent.ExecutionContext

class TodoController @Inject()(repo: TodoRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  val logger: Logger = Logger(getClass)

  def findById(id: Long): Action[AnyContent] = Action.async {
    repo.findById(id).map {
      case Some(todo) => Ok(Json.toJson(todo))
      case None => NotFound(Json.obj("error" -> s"ID $id not found"))
    }.recover { case ex =>
      logger.error(s"Failed to get ID $id", ex)
      InternalServerError(Json.obj("error" -> s"Failed unexpectedly when searching for ID $id"))
    }
  }
}
