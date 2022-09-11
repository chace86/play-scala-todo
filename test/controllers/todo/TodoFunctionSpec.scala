package controllers.todo

import models.todo.Todo
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import play.api.test.Helpers._

class TodoFunctionSpec extends PlaySpec with GuiceOneAppPerSuite {

  private val ID = 1

  "GET /todo" should {

    "return 200 and todo for valid id" in {
      val request = FakeRequest(GET, s"/todo/$ID")
      val result  = route(app, request).get
      status(result) must equal(OK)
      contentAsJson(result).as[Todo] must equal(Todo(ID, "shirt", isCompleted = false))
    }

    "return 404 if not todo found for id" in {
      val request = FakeRequest(GET, s"/todo/123")
      val result  = route(app, request).get
      status(result) must equal(NOT_FOUND)
    }
  }

  "POST /todo" should {

    "return 201 for newly created todo" in {
      val description = "test-description"
      val isCompleted = false
      val request     = FakeRequest(POST, s"/todo?description=$description&isCompleted=$isCompleted")
      val result      = route(app, request).get
      status(result) must equal(CREATED)
      contentAsString(result) must equal("/todo/4")
    }

    "return 201 if isCompleted parameter is missing" in {
      val request = FakeRequest(POST, s"/todo?description=test")
      val result  = route(app, request).get
      status(result) must equal(CREATED)
    }

    "return 400 if description parameter is missing" in {
      val request = FakeRequest(POST, s"/todo?isCompleted=false")
      val result  = route(app, request).get
      status(result) must equal(BAD_REQUEST)
    }
  }

  "PUT /todo" should {

    "return 200 when given description to update" in {
      val request = FakeRequest(PUT, s"/todo/$ID?description=pants")
      val result  = route(app, request).get
      status(result) must equal(OK)
    }

    "return 200 when given isCompleted to update" in {
      val request = FakeRequest(PUT, s"/todo/$ID?isCompleted=true")
      val result  = route(app, request).get
      status(result) must equal(OK)
    }

    "return 404 if todo is not found" in {
      val request = FakeRequest(PUT, "/todo/123?description=pants")
      val result  = route(app, request).get
      status(result) must equal(NOT_FOUND)
    }
  }

  "DELETE /todo" should {

    "return 200 if todo deleted" in {
      val request = FakeRequest(DELETE, "/todo/3")
      val result  = route(app, request).get
      status(result) must equal(OK)
    }

    "return 404 if todo not found" in {
      val request = FakeRequest(DELETE, "/todo/123")
      val result  = route(app, request).get
      status(result) must equal(NOT_FOUND)
    }
  }
}
