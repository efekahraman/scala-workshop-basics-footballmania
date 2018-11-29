package workshop

import workshop.JsonWriter.toJson

import scala.util.{Failure, Success, Try}

object Main extends App {

  trait Location
  case object Home extends Location
  case object Away extends Location

  def getLocation(location: String): Try[Location] = ???

  def buildStats: Try[TeamStats] = ???

  def query(stats: TeamStats, team: String, location: Location): Try[List[Game]] = ???

  def readArg(index: Int): Try[String] = ???

  def getResult(): Try[List[Game]] = ???

  getResult() match {
    case Success(list)  => println(toJson(list))
    case Failure(error) => println(toJson(error))
  }



}
