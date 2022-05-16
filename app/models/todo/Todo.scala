package models.todo

import play.api.libs.json.{Json, OFormat}

case class Todo(id: Long, description: String, isCompleted: Boolean)

object Todo {
  implicit val format: OFormat[Todo] = Json.format[Todo]
}