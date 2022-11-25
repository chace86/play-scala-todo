package controllers.todo

import javax.inject.Inject

import models.response.ErrorResponse
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.AbstractController
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import repositories.todo.TodoListRepository

import scala.concurrent.ExecutionContext

class TodoListController @Inject() (repository: TodoListRepository, cc: ControllerComponents)(implicit
    ec: ExecutionContext
) extends AbstractController(cc)
    with Logging {

  def create(title: String): Action[AnyContent] = Action.async { request =>
    repository.create(title)
      .map(id => Created(request.path + s"/$id"))
      .recover { case ex =>
        logger.error("Failed to create resource", ex)
        InternalServerError(Json.toJson(ErrorResponse("Failed to create resource")))
      }
  }
}
