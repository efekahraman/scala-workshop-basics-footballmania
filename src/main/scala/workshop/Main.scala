package workshop

import workshop.JsonWriter.toJson

import scala.util.{Failure, Success, Try}

object Main extends App {

  def buildStats: Try[TeamStats] = ???

  def query(stats: TeamStats, team: String, attribute: String): Try[List[Game]] = ???

  def readArg(index: Int): Try[String] = ???

  def getResult(): Try[List[Game]] = ???

  getResult() match {
    case Success(list)  => println(toJson(list))
    case Failure(error) => println(toJson(error))
  }



}
