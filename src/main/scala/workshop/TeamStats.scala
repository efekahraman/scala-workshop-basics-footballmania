package workshop

class TeamStats(homeGamesMap: Map[String, List[Game]], awayGamesMap: Map[String, List[Game]]) {

  def homeGames(team: String): List[Game] = homeGamesMap.getOrElse(team, Nil)

  def awayGames(team: String): List[Game] = awayGamesMap.getOrElse(team, Nil)

  def allGames(team: String): List[Game] = homeGames(team) ++ awayGames(team)

}

object TeamStats {

  def apply(data: List[Game]): TeamStats = new TeamStats(data.groupBy(_.home), data.groupBy(_.away))


}