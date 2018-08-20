package object workshop {

  case class Score(home: Int, away: Int)

  case class Game(date: String, home: String, away: String, score: Score)

}
