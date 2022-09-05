package controllers.todo

import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play._
import play.api.http.Status.CREATED
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import repositories.todo.{Todo, TodoRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
class TodoControllerSpec extends PlaySpec with MockFactory {

  private val ID = 1
  private val todo = Todo(ID, "hello", isCompleted = false)
  private val path = "/todo"

  private val repository = mock[TodoRepository]
  private val controller = new TodoController(repository, Helpers.stubControllerComponents())

  "GET /todo" should {

    "return 200 and todo for valid id" in {
      (repository.findById _).expects(ID)
        .returning(Future.successful(Some(todo)))

      val result = controller.findById(ID).apply(FakeRequest(GET, path))
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
      (repository.findById _).expects(ID)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.findById(ID).apply(FakeRequest(GET, path))
      status(result) must equal(INTERNAL_SERVER_ERROR)
    }
  }

  "POST /todo" should {

    "return 201 when todo is created" in {
      val description = "test-description"
      val isCompleted = false
      (repository.create _).expects(description, isCompleted)
        .returning(Future.successful(1))

      val result = controller.create(description, isCompleted)
        .apply(FakeRequest(POST, path))

      status(result) must equal(CREATED)
      contentAsString(result) must equal(s"$path/$ID")
    }

    "return 500 if data source fails" in {
      (repository.create _).expects(*, *)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.create("", isCompleted = true).apply(FakeRequest(POST, path))
      status(result) must be (INTERNAL_SERVER_ERROR)
    }
  }

  "PUT /todo" should {

    "return 200 if todo updated" in {
      (repository.update _).expects(ID, Some("test"), Some(false))
        .returning(Future.successful(1))

      val result = controller.update(ID, Some("test"), Some(false)).apply(FakeRequest(PUT, path))
      status(result) must be(OK)
    }

    "return 404 if todo id not found" in {
      (repository.update _).expects(2, Some("test"), Some(false))
        .returning(Future.successful(0))

      val result = controller.update(2, Some("test"), Some(false)).apply(FakeRequest(PUT, path))
      status(result) must be(NOT_FOUND)
    }

    "return 500 if data source fails" in {
      (repository.update _).expects(ID, Some("test"), Some(false))
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.update(ID, Some("test"), Some(false)).apply(FakeRequest(PUT, path))
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }

  "DELETE /todo" should {

    "return 200 if rows are deleted" in {
      (repository.delete _).expects(ID)
        .returning(Future.successful(1))

      val result = controller.delete(ID).apply(FakeRequest(DELETE, path))
      status(result) must be(OK)
    }

    "return 404 if todo id not found" in {
      (repository.delete _).expects(2)
        .returning(Future.successful(0))

      val result = controller.delete(2).apply(FakeRequest(DELETE, path))
      status(result) must be(NOT_FOUND)
    }

    "return 500 if data source fails" in {
      (repository.delete _).expects(ID)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.delete(ID).apply(FakeRequest(DELETE, path))
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }
}
