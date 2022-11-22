package controllers.example

import javax.inject.Inject

import models.example.Example
import play.api.libs.json.JsError
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc.AbstractController
import play.api.mvc.Action
import play.api.mvc.ControllerComponents

class ExampleController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  // testing enum serialization and swagger display
  def example: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Example]
      .map(ex => Ok(Json.toJson(ex)))
      .recoverTotal(err => BadRequest(JsError.toJson(err)))
  }
}
