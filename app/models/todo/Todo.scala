package models.todo

import play.api.libs.json.Format
import play.api.libs.json.Json

case class Todo(id: Long, description: String, isCompleted: Boolean)

object Todo {
  implicit val format: Format[Todo] = Json.format[Todo]
}
