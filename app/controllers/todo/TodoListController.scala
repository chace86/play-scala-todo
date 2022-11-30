package controllers.todo

import javax.inject.Inject

import models.response.ErrorResponse
import models.response.MessageResponse
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

  def update(id: Long, title: String): Action[AnyContent] = Action.async {
    repository.update(id, title)
      .map(n =>
        if (n > 0) Ok(Json.toJson(MessageResponse(s"Updated $n rows")))
        else NotFound(Json.toJson(ErrorResponse(s"ID $id not found")))
      )
      .recover { case ex =>
        logger.error(s"Failed to update todo list with ID $id", ex)
        InternalServerError(Json.toJson(ErrorResponse(s"Failed to update resource with ID $id")))
      }
  }

  def findById(id: Long): Action[AnyContent] = Action.async {
    repository.findById(id)
      .map {
        case Some(list) => Ok(Json.toJson(list))
        case None       => NotFound(Json.toJson(ErrorResponse(s"ID $id not found")))
      }
      .recover { case ex =>
        logger.error(s"Failed to get todo list ID $id", ex)
        InternalServerError(Json.toJson(ErrorResponse(s"Failed to get resource with ID $id")))
      }
  }

  def delete(id: Long): Action[AnyContent] = Action.async {
    repository.delete(id)
      .map(n =>
        if (n > 0) Ok(Json.toJson(MessageResponse(s"Deleted $n rows")))
        else NotFound(Json.toJson(ErrorResponse(s"ID $id not found")))
      )
      .recover { case ex =>
        logger.error(s"Failed to delete todo list with ID $id", ex)
        InternalServerError(Json.toJson(ErrorResponse(s"Failed to delete resource with ID $id")))
      }
  }
}
