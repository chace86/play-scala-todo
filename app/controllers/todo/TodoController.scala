package controllers.todo

import javax.inject._

import models.todo.Todo
import play.api.libs.json.Json
import play.api.mvc._

// TODO: inject service or repo here
class TodoController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getTodo(id: Long): Action[AnyContent] = Action { implicit request =>
    Ok(Json.toJson(Todo(0, "hello world", isCompleted = true)))
  }
}
