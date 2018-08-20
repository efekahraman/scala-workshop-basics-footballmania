package workshop

import org.scalatest.FunSuite

class TeamStatsSuite extends FunSuite {

  test("Home matches should be aggregated successfully") {
    val list = List(
      Game("17 Aug 2017", "Juventus", "Cagliari Calcio", Score(3, 0)),
      Game("9 Sep 2017", "Juventus", "AC Chievo Verona", Score(3, 0)),
      Game("1 Oct 2017", "Atalanta BC", "Juventus", Score(2, 2))
    )

    val homeGames = TeamStats(list).homeGames("Juventus")
    assert(homeGames.isDefined)
    assert(homeGames.get.size === 2)
  }

  test("Away matches should be aggregated successfully") {
    val list = List(
      Game("20 Aug 2017", "FC Internazionale Milano", "ACF Fiorentina", Score(3, 0)),
      Game("9 Sep 2017", "Juventus", "ACF Fiorentina", Score(1, 0)),
      Game("1 Oct 2017", "AS Roma", "FC Internazionale Milano", Score(1, 3))
    )

    val awayGames = TeamStats(list).awayGames("ACF Fiorentina")
    assert(awayGames.isDefined)
    assert(awayGames.get.size === 2)
  }

  test("All matches should be aggregated successfully") {
    val list = List(
      Game("17 Aug 2017", "Juventus", "Cagliari Calcio", Score(3, 0)),
      Game("9 Sep 2017", "Juventus", "AC Chievo Verona", Score(3, 0)),
      Game("1 Oct 2017", "Atalanta BC", "Juventus", Score(2, 2)),
      Game("1 Oct 2017", "AS Roma", "FC Internazionale Milano", Score(1, 3))
    )

    val allGames = TeamStats(list).allGames("Juventus")
    assert(allGames.isDefined)
    assert(allGames.get.size === 3)
  }

  test("All matches should be aggregated successfully when only home matches are given") {
    val list = List(
      Game("17 Aug 2017", "Juventus", "Cagliari Calcio", Score(3, 0)),
      Game("9 Sep 2017", "Juventus", "AC Chievo Verona", Score(3, 0)),
      Game("1 Oct 2017", "AS Roma", "FC Internazionale Milano", Score(1, 3))
    )

    val allGames = TeamStats(list).allGames("Juventus")
    assert(allGames.isDefined)
    assert(allGames.get.size === 2)
  }

  test("All matches should be aggregated successfully when only away matches are given") {
    val list = List(
      Game("1 Oct 2017", "Atalanta BC", "Juventus", Score(2, 2)),
      Game("1 Oct 2017", "AS Roma", "FC Internazionale Milano", Score(1, 3))
    )

    val allGames = TeamStats(list).allGames("Juventus")
    assert(allGames.isDefined)
    assert(allGames.get.size === 1)
  }

  test("An unknown team's aggregation should return empty") {
    val list = List(
      Game("17 Aug 2017", "Juventus", "Cagliari Calcio", Score(3, 0)),
      Game("9 Sep 2017", "Juventus", "AC Chievo Verona", Score(3, 0)),
      Game("1 Oct 2017", "Atalanta BC", "Juventus", Score(2, 2))
    )

    val stats = TeamStats(list)
    assert(stats.allGames("FC Barcelona").isEmpty)
    assert(stats.awayGames("FC Barcelona").isEmpty)
    assert(stats.homeGames("FC Barcelona").isEmpty)
  }

}
