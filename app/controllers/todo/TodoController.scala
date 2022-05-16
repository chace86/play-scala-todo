package controllers.todo

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._
import repositories.todo.TodoRepository

import scala.concurrent.ExecutionContext

// TODO: inject service or repo here
class TodoController @Inject()(repo: TodoRepository, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def getTodo(id: Long): Action[AnyContent] = Action.async { implicit request =>
    repo.getTodo(id).map {
      case Some(todo) => Ok(Json.toJson(todo))
      case None => NotFound
    }.recover { case _ => InternalServerError}
  }
}
