package models.todo

import play.api.libs.json.Format
import play.api.libs.json.Json

case class Todo(id: Option[Long], description: String, todoListId: Long, isCompleted: Option[Boolean] = None)

object Todo {
  implicit val format: Format[Todo] = Json.format[Todo]
}
