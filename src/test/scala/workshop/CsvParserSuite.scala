package workshop

import java.nio.file.Paths

import org.scalatest.FunSuite

class CsvParserSuite extends FunSuite {

  test("A file should be read successfully") {
    val filename = Paths.get(getClass.getClassLoader.getResource("test-seriea.csv").toURI).toFile.getCanonicalPath
    val file = CsvParser.readFile(filename)
    assert(file.isSuccess)
    assert(file.get.size === 6)
  }

  test("Unknown file should give an error while reading") {
    val file = CsvParser.readFile("blah")
    assert(file.isFailure)
  }

  test("A valid CSV line should be parsed successfully") {
    val line = "?,(Sat) 19 Aug 2017 (33),Juventus (1),3-0,2-0,Cagliari Calcio (1)"
    val game = CsvParser.parseLine(line)
    assert(game.isSuccess)
    assert(game.get.home === "Juventus")
    assert(game.get.away === "Cagliari Calcio")
    assert(game.get.score === Score(3, 0))
    assert(game.get.date  === "19 Aug 2017")
  }

  test("An invalid CSV line should give an error") {
    val missingColumn = "?,(Sat) 19 Aug 2017 (33),Juventus (1), 2-0,Cagliari Calcio (1)"
    val result = CsvParser.parseLine(missingColumn)
    assert(result.isFailure)
  }

  test("Multiple valid CSV lines should be parsed successfully") {
    val lines= List(
      "?,(Sat) 19 Aug 2017 (33),Juventus (1),3-0,2-0,Cagliari Calcio (1)",
      "?,(Sat) 26 Aug 2017 (34),Benevento (2),0-1,0-0,Bologna FC (2)",
      "?,(Sat) 26 Aug 2017 (34),AS Roma (2),1-3,1-0,FC Internazionale Milano (2)"
    )

    val games = CsvParser.parseFile(lines)
    assert(games.isSuccess)
    assert(games.get.size === 3)
  }

  test("Multiple CSV lines having at least one invalid line should give an error") {
    val lines= List(
      "?,(Sat) 26 Aug 2017 (34),Benevento (2),0-1,0-0,Bologna FC (2)",
      "?,(Sat) 26 Aug 2017 (34),AS Roma (2),1-3,1-0,FC Internazionale Milano (2)"
    )

    val missingColumn = "?,(Sat) 19 Aug 2017 (33),Juventus (1), 2-0,Cagliari Calcio (1)"

    val games = CsvParser.parseFile(lines :+ missingColumn)
    assert(games.isFailure)
  }

}
