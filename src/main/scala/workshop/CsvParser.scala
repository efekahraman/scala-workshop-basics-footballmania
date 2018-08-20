package workshop

import scala.util.Try

object CsvParser {

  def readFile(filename: String): Try[List[String]] = ???

  def parseLine(line: String): Try[Game] = ???

  def parseFile(lines: List[String]): Try[List[Game]] = ???

}
