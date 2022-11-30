package controllers.todo

import javax.inject._

import models.response.ErrorResponse
import models.response.MessageResponse
import models.todo.Todo
import play.api.Logging
import play.api.libs.json.JsError
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc._
import repositories.todo.TodoRepository

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class TodoController @Inject() (repository: TodoRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  private val AllowedUpdateKeys = Set("description", "isCompleted")

  def create: Action[JsValue] = Action(parse.json).async { request =>
    request.body.validate[Todo]
      .map(
        repository.create(_)
          .map(id => Created(request.path + s"/$id"))
          .recover { case ex =>
            logger.error("Failed to create resource", ex)
            InternalServerError(Json.toJson(ErrorResponse("Failed to create resource")))
          }
      )
      .recoverTotal(error => Future.successful(BadRequest(JsError.toJson(error))))
  }

  def update(id: Long, description: Option[String], isCompleted: Option[Boolean]): Action[AnyContent] = Action.async {

    if (description.isEmpty && isCompleted.isEmpty)
      Future.successful(BadRequest(Json.toJson(ErrorResponse("At least one query parameter required"))))
    else {
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
  }

  def findById(id: Long): Action[AnyContent] = Action.async {
    repository.findById(id)
      .map {
        case Some(todo) => Ok(Json.toJson(todo))
        case None       => NotFound(Json.toJson(ErrorResponse(s"ID $id not found")))
      }
      .recover { case ex =>
        logger.error(s"Failed to get todo ID $id", ex)
        InternalServerError(Json.toJson(ErrorResponse(s"Failed to get resource with ID $id")))
      }
  }
  // TODO: add pagination
  def findAllByListId(todoListId: Long): Action[AnyContent] = Action.async {
    repository.findAllByListId(todoListId)
      .map(todos => Ok(Json.toJson(todos)))
      .recover { case ex =>
        logger.error(s"Failed to get todos by todo list ID $todoListId", ex)
        InternalServerError(Json.toJson(ErrorResponse(s"Failed to get resource with ID $todoListId")))
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
