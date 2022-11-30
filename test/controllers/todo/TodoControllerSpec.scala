package controllers.todo

import models.todo.Todo
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import repositories.todo.TodoRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
class TodoControllerSpec extends PlaySpec with MockFactory {

  private val id      = 1
  private val todo    = Todo(Some(id), "hello", 1)
  private val newTodo = todo.copy(id = None)
  private val path    = "/todo"

  private val repository = mock[TodoRepository]
  private val controller = new TodoController(repository, Helpers.stubControllerComponents())

  s"GET $path" should {

    val request = FakeRequest(GET, path)

    "return 200 and todo for valid id" in {
      (repository.findById _).expects(id)
        .returning(Future.successful(Some(todo)))

      val result = controller.findById(id).apply(request)
      status(result) must be(OK)
      contentAsJson(result).as[Todo] must be(todo)
    }

    "return 404 if id does not exist" in {
      (repository.findById _).expects(id)
        .returning(Future.successful(None))

      val result = controller.findById(id).apply(request)
      status(result) must be(NOT_FOUND)
    }

    "return 500 if data source fails" in {
      (repository.findById _).expects(id)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.findById(id).apply(request)
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }

  s"POST $path" should {

    val request = FakeRequest(POST, path)

    "return 201 when todo is created" in {
      (repository.create _).expects(newTodo)
        .returning(Future.successful(1))

      val result = controller.create.apply(request.withBody(Json.toJson(newTodo)))

      status(result) must be(CREATED)
      contentAsString(result) must be(s"$path/$id")
    }

    "return 500 if data source fails" in {
      (repository.create _).expects(*)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.create().apply(request.withBody(Json.toJson(todo)))

      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }

  s"PUT $path" should {

    val request = FakeRequest(PUT, path)

    "return 200 if todo updated" in {
      (repository.update _).expects(id, Some("test"), Some(false))
        .returning(Future.successful(1))

      val result = controller.update(id, Some("test"), Some(false)).apply(request)
      status(result) must be(OK)
    }

    "return 404 if todo id not found" in {
      (repository.update _).expects(2, Some("test"), Some(false))
        .returning(Future.successful(0))

      val result = controller.update(2, Some("test"), Some(false)).apply(request)
      status(result) must be(NOT_FOUND)
    }

    "return 500 if data source fails" in {
      (repository.update _).expects(id, Some("test"), Some(false))
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.update(id, Some("test"), Some(false)).apply(request)
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }

  s"DELETE $path" should {

    val request = FakeRequest(DELETE, path)

    "return 200 if rows are deleted" in {
      (repository.delete _).expects(id)
        .returning(Future.successful(1))

      val result = controller.delete(id).apply(request)
      status(result) must be(OK)
    }

    "return 404 if todo id not found" in {
      (repository.delete _).expects(2)
        .returning(Future.successful(0))

      val result = controller.delete(2).apply(request)
      status(result) must be(NOT_FOUND)
    }

    "return 500 if data source fails" in {
      (repository.delete _).expects(id)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.delete(id).apply(request)
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }
}
