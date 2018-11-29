package workshop

import java.nio.charset.Charset

import scala.annotation.tailrec
import scala.io.Source
import scala.util.{Failure, Success, Try}

object CsvParser {

  case class CsvParseException(message: String) extends Exception(message)

  private val DateRegex  = """\([\w]{3}\) ([\d]{1,2}\s[\w]{3}\s[\d]{4})\s\([\d]{1,2}\)""".r
  private val ScoreRegex = """([\d]{1,2})-([\d]{1,2})""".r
  private val TeamRegex  = """([\w\d\s]+)\s\([\d]{1,2}\)""".r

  def readFile(filename: String): Try[List[String]] = Try(Source.fromFile(filename)(Charset.defaultCharset()).getLines().toList)

  def parseLine(line: String): Try[Game] = {
    val tokens = line.split(",")
    if (tokens.length != 6) Failure(CsvParseException(s"Invalid line: $line"))
    else {

      val dateMatch = DateRegex.pattern.matcher(tokens(1))
      val dateTry   = if (dateMatch.find()) Success(dateMatch.group(1)) else Failure(CsvParseException(s"Couldn't parse date! Line: $line"))

      val scoreMatch = ScoreRegex.pattern.matcher(tokens(3))
      val scoreTry   =
        if (scoreMatch.find())
          for {
            home <- Try(scoreMatch.group(1).toInt)
            away <- Try(scoreMatch.group(2).toInt)
          } yield Score(home, away)
        else Failure(CsvParseException(s"Couldn't parse score! Line: $line"))

      def parseTeam(column: String): Try[String] = {
        val teamMatch = TeamRegex.pattern.matcher(column)
        if (teamMatch.find()) Success(teamMatch.group(1)) else Failure(CsvParseException(s"Couldn't parse team! Line: $line"))
      }

      for {
        date  <- dateTry
        score <- scoreTry
        home  <- parseTeam(tokens(2))
        away  <- parseTeam(tokens(5))
      } yield Game(date, home, away, score)
    }
  }

  def parseFile(lines: List[String]): Try[List[Game]] = {
    val parsedLines = lines.drop(1).map(parseLine)

    @tailrec
    def parseFileRec(list: List[Try[Game]], acc: List[Game]): Try[List[Game]] = list match {
      case x :: xs =>
        x match {
          case Success(game) => parseFileRec(xs, acc :+ game)
          case Failure(err)  => Failure(err)
        }
      case Nil     => Success(acc)
    }

    parseFileRec(parsedLines, Nil)
  }

}
