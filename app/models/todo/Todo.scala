package models.todo

import play.api.libs.json.Format
import play.api.libs.json.Json

// default param normally at end, but "feels weird" for entity model
case class Todo(id: Option[Long], description: String, todoListId: Long, isCompleted: Option[Boolean] = None)

object Todo {
  implicit val format: Format[Todo] = Json.format[Todo]
}
