package controllers.todo

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._
import repositories.todo.TodoRepository

import scala.concurrent.ExecutionContext

// TODO: need to add logging
class TodoController @Inject()(repo: TodoRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def getTodo(id: Long): Action[AnyContent] = Action.async {
    repo.getTodo(id).map {
      case Some(todo) => Ok(Json.toJson(todo))
      case None => NotFound(Json.obj("error" -> s"ID $id not found"))
    }.recover { case _ =>
      InternalServerError(Json.obj("error" -> s"Failed unexpectedly when searching for ID $id"))
    }
  }
}
