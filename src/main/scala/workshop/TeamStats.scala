package workshop

class TeamStats(/* ??? */) {

  def homeGames(team: String): Option[List[Game]] = ???

  def awayGames(team: String): Option[List[Game]] = ???

  def allGames(team: String): Option[List[Game]] = ???

}

object TeamStats {

  def apply(data: List[Game]): TeamStats = ???

}