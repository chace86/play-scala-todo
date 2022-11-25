package controllers.todo

import models.todo.Todo
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play._
import play.api.http.Status.CREATED
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import repositories.todo.TodoRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
class TodoControllerSpec extends PlaySpec with MockFactory {

  private val id         = 1
  private val todoListId = 1
  private val todo       = Todo(id, "hello", isCompleted = false, 1)
  private val path       = "/todo"

  private val repository = mock[TodoRepository]
  private val controller = new TodoController(repository, Helpers.stubControllerComponents())

  "GET /todo" should {

    "return 200 and todo for valid id" in {
      (repository.findById _).expects(id)
        .returning(Future.successful(Some(todo)))

      val result = controller.findById(id).apply(FakeRequest(GET, path))
      status(result) must equal(OK)
      contentAsJson(result) must equal(Json.toJson(todo))
    }

    "return 404 if id does not exist" in {
      (repository.findById _).expects(2)
        .returning(Future.successful(None))

      val result = controller.findById(2).apply(FakeRequest(GET, path))
      status(result) must equal(NOT_FOUND)
    }

    "return 500 if data source fails" in {
      (repository.findById _).expects(id)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.findById(id).apply(FakeRequest(GET, path))
      status(result) must equal(INTERNAL_SERVER_ERROR)
    }
  }

  "POST /todo" should {

    "return 201 when todo is created" in {
      val description = "test-description"
      val isCompleted = false
      (repository.create _).expects(description, isCompleted, todoListId)
        .returning(Future.successful(1))

      val result = controller.create(description, isCompleted, todoListId)
        .apply(FakeRequest(POST, path))

      status(result) must equal(CREATED)
      contentAsString(result) must equal(s"$path/$id")
    }

    "return 500 if data source fails" in {
      (repository.create _).expects(*, *, *)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.create("", isCompleted = true, todoListId).apply(FakeRequest(POST, path))
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }

  "PUT /todo" should {

    "return 200 if todo updated" in {
      (repository.update _).expects(id, Some("test"), Some(false))
        .returning(Future.successful(1))

      val result = controller.update(id, Some("test"), Some(false)).apply(FakeRequest(PUT, path))
      status(result) must be(OK)
    }

    "return 404 if todo id not found" in {
      (repository.update _).expects(2, Some("test"), Some(false))
        .returning(Future.successful(0))

      val result = controller.update(2, Some("test"), Some(false)).apply(FakeRequest(PUT, path))
      status(result) must be(NOT_FOUND)
    }

    "return 500 if data source fails" in {
      (repository.update _).expects(id, Some("test"), Some(false))
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.update(id, Some("test"), Some(false)).apply(FakeRequest(PUT, path))
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }

  "DELETE /todo" should {

    "return 200 if rows are deleted" in {
      (repository.delete _).expects(id)
        .returning(Future.successful(1))

      val result = controller.delete(id).apply(FakeRequest(DELETE, path))
      status(result) must be(OK)
    }

    "return 404 if todo id not found" in {
      (repository.delete _).expects(2)
        .returning(Future.successful(0))

      val result = controller.delete(2).apply(FakeRequest(DELETE, path))
      status(result) must be(NOT_FOUND)
    }

    "return 500 if data source fails" in {
      (repository.delete _).expects(id)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.delete(id).apply(FakeRequest(DELETE, path))
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }
}
