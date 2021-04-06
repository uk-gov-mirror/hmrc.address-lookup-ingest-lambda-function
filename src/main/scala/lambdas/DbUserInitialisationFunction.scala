package lambdas

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import repositories.{AdminRepository, Repository}

import java.util.{Map => jMap}
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class DbUserInitialisationFunction extends RequestHandler[jMap[String, String], Unit] {
  override def handleRequest(notUsed: jMap[String, String], contextNotUsed: Context): Unit = {
    Await.result(initialiseDbUsers(Repository.forAdmin()), 5.seconds)
  }

  private[lambdas] def initialiseDbUsers(repository: AdminRepository) = {
    repository.initialiseUsers()
  }
}

object DbUserInitialisationFunction extends App {
  new DbUserInitialisationFunction().handleRequest(null, null)
}