package controllers.todo

import models.todo.Todo
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class TodoFunSpec extends PlaySpec with GuiceOneAppPerSuite {

  private val id         = 1
  private val todoListId = 1
  private val path       = "/todo"

  "GET /todo" should {

    "return 200 and todo for valid id" in {
      val request = FakeRequest(GET, s"$path/$id")
      val result  = route(app, request).get
      status(result) must equal(OK)
      contentAsJson(result).as[Todo] must equal(Todo(Some(id), "Shirt", todoListId, Some(false)))
    }

    "return 404 if not todo found for id" in {
      val request = FakeRequest(GET, s"$path/123")
      val result  = route(app, request).get
      status(result) must equal(NOT_FOUND)
    }
  }

  "POST /todo" should {

    "return 201 for newly created todo" in {
      val newTodo = Todo(None, "test-description", 1)
      val result  = route(app, FakeRequest(POST, path).withBody(Json.toJson(newTodo))).get
      status(result) must equal(CREATED)
      contentAsString(result) must equal(s"$path/10")
    }

    "return 201 if isCompleted parameter is missing" in {
      val request = FakeRequest(POST, path).withBody(Json.obj("description" -> "test", "todoListId" -> todoListId))
      val result  = route(app, request).get
      println(contentAsString(result))
      status(result) must equal(CREATED)
    }

    "return 400 if description parameter is missing" in {
      val request = FakeRequest(POST, path).withBody(Json.obj("isCompleted" -> false, "todoListId" -> todoListId))
      val result  = route(app, request).get
      status(result) must equal(BAD_REQUEST)
    }

    "return 400 if todoListId parameter is missing" in {
      val request = FakeRequest(POST, path).withBody(Json.obj("isCompleted" -> false, "description" -> "test"))
      val result  = route(app, request).get
      status(result) must equal(BAD_REQUEST)
    }
  }

  "PUT /todo" should {

    "return 200 when given description to update" in {
      val request = FakeRequest(PUT, s"$path/$id?description=pants")
      val result  = route(app, request).get
      status(result) must equal(OK)
    }

    "return 200 when given isCompleted to update" in {
      val request = FakeRequest(PUT, s"$path/$id?isCompleted=true")
      val result  = route(app, request).get
      status(result) must equal(OK)
    }

    "return 404 if todo is not found" in {
      val request = FakeRequest(PUT, s"$path/123?description=pants")
      val result  = route(app, request).get
      status(result) must equal(NOT_FOUND)
    }
  }

  "DELETE /todo" should {

    "return 200 if todo deleted" in {
      val request = FakeRequest(DELETE, s"$path/3")
      val result  = route(app, request).get
      status(result) must equal(OK)
    }

    "return 404 if todo not found" in {
      val request = FakeRequest(DELETE, s"$path/123")
      val result  = route(app, request).get
      status(result) must equal(NOT_FOUND)
    }
  }
}
