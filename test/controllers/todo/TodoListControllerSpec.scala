package controllers.todo

import models.todo.TodoList
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.FakeRequest
import play.api.test.Helpers
import repositories.todo.TodoListRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TodoListControllerSpec extends PlaySpec with MockFactory {

  private val id    = 1
  private val title = "Groceries"
  private val list  = TodoList(id, title)
  private val path  = "todo/list"

  private val repository = mock[TodoListRepository]
  private val controller = new TodoListController(repository, Helpers.stubControllerComponents())

  s"POST $path" should {

    val request = FakeRequest(POST, path)

    "return 201 when list is created" in {
      (repository.findById _).expects(id)
        .returning(Future.successful(Some(list)))

      val result = controller.findById(id).apply(request)
      status(result) must be(OK)
      contentAsJson(result) must be(Json.toJson(list))
    }

    "return 500 if data source fails" in {
      (repository.create _).expects(*)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.create(title).apply(request)
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }

  s"PUT $path" should {

    val request = FakeRequest(PUT, path)

    "return 200 if list updated" in {
      (repository.update _).expects(id, title)
        .returning(Future.successful(1))

      val result = controller.update(id, title).apply(request)
      status(result) must be(OK)
    }

    "return 404 if id not found" in {
      (repository.update _).expects(id, title)
        .returning(Future.successful(0))

      val result = controller.update(id, title).apply(request)
      status(result) must be(NOT_FOUND)
    }

    "return 500 if data source fails" in {
      (repository.update _).expects(id, title)
        .returning(Future.failed(new Exception("Test failure")))

      val result = controller.update(id, title).apply(request)
      status(result) must be(INTERNAL_SERVER_ERROR)
    }
  }

  s"GET $path" should {

    val request = FakeRequest(GET, path)

    "return 200 and list for valid id" in {
      (repository.findById _).expects(id)
        .returning(Future.successful(Some(list)))

      val result = controller.findById(id).apply(request)
      status(result) must be(OK)
      contentAsJson(result).as[TodoList] must be(list)
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

  s"DELETE $path" should {

    val request = FakeRequest(DELETE, path)

    "return 200 if deleted" in {
      (repository.delete _).expects(id)
        .returning(Future.successful(1))

      val result = controller.delete(id).apply(request)
      status(result) must be(OK)
    }

    "return 404 if id not found" in {
      (repository.delete _).expects(id)
        .returning(Future.successful(0))

      val result = controller.delete(id).apply(request)
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
