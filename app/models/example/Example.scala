package models.example

import models.status.Status.Status
import play.api.libs.json.Format
import play.api.libs.json.Json

case class Example(status: Status, other: String)

object Example {
  implicit val f: Format[Example] = Json.format[Example]
}
