package models.todo

import play.api.libs.json.Format
import play.api.libs.json.Json

case class TodoList(id: Long, title: String)

object TodoList {
  implicit val f: Format[TodoList] = Json.format[TodoList]
}
