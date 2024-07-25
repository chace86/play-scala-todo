package user

import play.api.mvc._
import com.google.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class UserRequest[A](val username: Option[String], request: Request[A]) extends WrappedRequest[A](request)

class UserAction @Inject() (val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent]
  with ActionTransformer[Request, UserRequest] {
  
  def transform[A](request: Request[A]): Future[UserRequest[A]] =
    Future.successful(new UserRequest(request.session.get("username"), request))
}
