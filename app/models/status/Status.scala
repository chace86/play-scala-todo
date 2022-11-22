package models.status

import play.api.libs.json.Format
import play.api.libs.json.Json

object Status extends Enumeration {
  type Status = Value
  val Success: Status    = Value("success")
  val InProgress: Status = Value("in_progress")
  val Fail: Status       = Value("fail")

  implicit val f: Format[Status] = Json.formatEnum(Status)
}
