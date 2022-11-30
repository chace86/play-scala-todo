package controllers.todo

import models.todo.TodoList
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import play.api.test.Helpers._

class TodoListFunSpec extends PlaySpec with GuiceOneAppPerSuite {

  private val id   = 1
  private val path = "/todo/list"

  s"GET $path" should {

    "return 200 and todo for valid id" in {
      val request = FakeRequest(GET, s"$path/$id")
      val result  = route(app, request).get
      status(result) must be(OK)
      contentAsJson(result).as[TodoList] must be(TodoList(id, "Clothes to buy"))
    }

    "return 404 if no list found for id" in {
      val request = FakeRequest(GET, s"$path/123")
      val result  = route(app, request).get
      status(result) must be(NOT_FOUND)
    }
  }

  s"POST $path" should {

    "return 201 for newly created list" in {
      val title  = "test-title"
      val result = route(app, FakeRequest(POST, s"$path?title=$title")).get
      status(result) must be(CREATED)
      contentAsString(result) must be(s"$path/3")
    }

    "return 400 if title parameter is missing" in {
      val result = route(app, FakeRequest(POST, s"$path")).get
      status(result) must be(BAD_REQUEST)
    }
  }

  s"PUT $path" should {

    "return 200 given a title to update" in {
      val result = route(app, FakeRequest(PUT, s"$path/$id?title=updated")).get
      status(result) must be(OK)
    }

    "return 400 when title query parameter missing" in {
      val result = route(app, FakeRequest(PUT, s"$path/$id")).get
      status(result) must be(BAD_REQUEST)
    }

    "return 404 if list not found" in {
      val result = route(app, FakeRequest(PUT, s"$path/123?title=updated")).get
      status(result) must be(NOT_FOUND)
    }
  }

  s"DELETE $path" should {

    "return 200 if list deleted" in {
      val result = route(app, FakeRequest(DELETE, s"$path/2")).get
      status(result) must be(OK)
    }

    "return 404 if list not found" in {
      val result = route(app, FakeRequest(DELETE, s"$path/123")).get
      status(result) must be(NOT_FOUND)
    }
  }
}
