package controllers.todo

import models.todo.{Todo, TodoRepository}
import org.scalamock.scalatest.MockFactory
import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
class TodoControllerSpec extends PlaySpec with MockFactory {

  private val ID = 1
  private val todo = Todo(ID, "hello", isCompleted = false)

  private val mockRepository = mock[TodoRepository]

  "TodoController GET" should {

    "return Ok and todo data if id exists" in {
      (mockRepository.findById _).expects(ID)
        .returning(Future.successful(Some(todo)))

      val controller = new TodoController(mockRepository, Helpers.stubControllerComponents())
      val result = controller.findById(ID).apply(FakeRequest(GET, "/todo"))

      status(result) must equal(OK)
      contentAsJson(result) must equal(Json.toJson(todo))
    }

    "return NotFound if id does not exist" in {
      (mockRepository.findById _).expects(2)
        .returning(Future.successful(None))

      val controller = new TodoController(mockRepository, Helpers.stubControllerComponents())
      val result = controller.findById(2).apply(FakeRequest(GET, "/todo"))

      status(result) must equal(NOT_FOUND)
    }

    "return InternalServerError if data source fails" in {
      (mockRepository.findById _).expects(ID)
        .returning(Future.failed(new Exception("Test failure")))

      val controller = new TodoController(mockRepository, Helpers.stubControllerComponents())
      val result = controller.findById(ID).apply(FakeRequest(GET, "/todo"))

      status(result) must equal(INTERNAL_SERVER_ERROR)
    }
  }
}
