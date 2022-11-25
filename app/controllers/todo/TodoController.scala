package controllers.todo

import javax.inject._

import models.response.ErrorResponse
import models.response.MessageResponse
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc._
import repositories.todo.TodoRepository

import scala.concurrent.ExecutionContext

class TodoController @Inject() (repository: TodoRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  def create(description: String, isCompleted: Boolean, todoListId: Long): Action[AnyContent] = Action.async {
    request =>
      repository.create(description, isCompleted, todoListId)
        .map(id => Created(request.path + s"/$id"))
        .recover { case ex =>
          logger.error("Failed to create resource", ex)
          InternalServerError(Json.toJson(ErrorResponse("Failed to create resource")))
        }
  }

  def update(id: Long, description: Option[String], isCompleted: Option[Boolean]): Action[AnyContent] = Action.async {
    repository.update(id, description, isCompleted)
      .map(n =>
        if (n > 0) Ok(Json.toJson(MessageResponse(s"Updated $n rows")))
        else NotFound(Json.toJson(ErrorResponse(s"ID $id not found")))
      )
      .recover { case ex =>
        logger.error(s"Failed to update todo with ID $id", ex)
        InternalServerError(Json.toJson(ErrorResponse(s"Failed to update resource with ID $id")))
      }
  }

  def findById(id: Long): Action[AnyContent] = Action.async {
    repository.findById(id).map {
      case Some(todo) => Ok(Json.toJson(todo))
      case None       => NotFound(Json.toJson(ErrorResponse(s"ID $id not found")))
    }.recover { case ex =>
      logger.error(s"Failed to get todo ID $id", ex)
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
        logger.error(s"Failed to delete todo with ID $id", ex)
        InternalServerError(Json.toJson(ErrorResponse(s"Failed to delete resource with ID $id")))
      }
  }
}
