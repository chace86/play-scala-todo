package repositories.user

import scala.collection.mutable

case class User(username: String, password: String)

class UserRepository {
  
    // TODO: replace with database
    private val users: mutable.Map[String, User] = mutable.Map()

    def getUser(username: String): Option[User] = users.get(username)

    def createUser(username: String, password: String): Boolean = {
        !users.contains(username) && users.put(username, User(username, password)).isDefined
    }
}
