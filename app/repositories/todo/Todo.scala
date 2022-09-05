package repositories.todo

import play.api.libs.json.{Format, Json}

case class Todo(id: Long, description: String, isCompleted: Boolean)

object Todo {
  implicit val format: Format[Todo] = Json.format[Todo]
}