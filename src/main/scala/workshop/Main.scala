package workshop

import workshop.JsonWriter.toJson

import scala.util.{Failure, Success, Try}

object Main extends App {

  trait Location
  case object Home extends Location
  case object Away extends Location

  def buildStats: Try[TeamStats] =
    CsvParser.readFile("./test-seriea.csv")
      .flatMap(CsvParser.parseFile)
      .map(TeamStats(_))

  def getLocation(location: String): Try[Location] = location match {
    case "home" => Success(Home)
    case "away" => Success(Away)
    case _      => Failure(new IllegalArgumentException(s"Location should be either home or away! Given: $location"))
  }

  def query(stats: TeamStats, team: String, location: Location): List[Game] = location match {
    case Home => stats.homeGames(team)
    case Away => stats.awayGames(team)
  }

  def readArg(index: Int): Try[String] =
    if (args.length < (index + 1)) Failure(new IllegalArgumentException("Not enough parameters!"))
    else                           Success(args(index))

  def getResult(): Try[List[Game]] = for {
    stats           <- buildStats
    team            <- readArg(0)
    locationString  <- readArg(1)
    location        <- getLocation(locationString)
  } yield query(stats, team, location)


  getResult() match {
    case Success(list)  => println(toJson(list))
    case Failure(error) => println(toJson(error))
  }

}
