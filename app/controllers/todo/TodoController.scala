package controllers.todo

import javax.inject._
import models.todo.TodoRepository

import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

class TodoController @Inject()(repository: TodoRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  private val logger = Logger(getClass)

  def create(description: String, isCompleted: Boolean): Action[AnyContent] = Action.async { implicit request =>
    // TODO: test what happens if conflict?
    repository.create(description, isCompleted)
      .map(id => Created(request.path + s"/$id"))
      .recover { case ex =>
        logger.error(s"Failed to create resource", ex)
        InternalServerError(Json.obj("error" -> s"Failed to create resource"))
      }
  }

  def update(id: Long, description: Option[String], isCompleted: Option[Boolean]): Action[AnyContent] = Action.async {
    repository.update(id, description, isCompleted)
      .map(n =>
        if (n > 0) Ok(Json.obj("message" -> s"Updated $n rows"))
        else       NotFound(Json.obj("error" -> s"ID $id not found")))
      .recover { case ex =>
        logger.error(s"Failed to update resource", ex)
        InternalServerError(Json.obj("error" -> s"Failed to update resource"))
      }
  }

  def findById(id: Long): Action[AnyContent] = Action.async {
    repository.findById(id).map {
      case Some(todo) => Ok(Json.toJson(todo))
      case None       => NotFound(Json.obj("error" -> s"ID $id not found"))
    }.recover { case ex =>
      logger.error(s"Failed to get ID $id", ex)
      InternalServerError(Json.obj("error" -> s"Failed unexpectedly when searching for ID $id"))
    }
  }

  def delete(id: Long): Action[AnyContent] = Action.async {
    repository.delete(id)
      .map(n =>
        if (n > 0) Ok(Json.obj("message" -> s"Deleted $n rows"))
        else       NotFound(Json.obj("error" -> s"ID $id not found")))
      .recover { case ex =>
        logger.error(s"Failed to delete resource", ex)
        InternalServerError(Json.obj("error" -> s"Failed to delete resource"))
      }
  }
}
