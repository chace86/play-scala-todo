package models.response

import play.api.libs.json.Format
import play.api.libs.json.Json

case class ErrorResponse(error: String)

object ErrorResponse {
  implicit val formats: Format[ErrorResponse] = Json.format[ErrorResponse]
}
