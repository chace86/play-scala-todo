package models.response

import play.api.libs.json.Format
import play.api.libs.json.Json

case class MessageResponse(message: String)

object MessageResponse {
  implicit val formats: Format[MessageResponse] = Json.format[MessageResponse]
}
