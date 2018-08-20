package workshop

import org.scalatest.FunSuite

class JsonWriterSuite extends FunSuite {

  test("An list of games should be serialized successfully") {
    val list = List (
      Game("19 Aug 2017", "Juventus", "AS Roma", Score(3, 0)),
      Game("19 Aug 2017", "Benevento", "Bologna FC", Score(0, 1))
    )

    val json = JsonWriter.toJson(list)
    val expected = """[{"date":"19 Aug 2017","home":"Juventus","away":"AS Roma","score":{"home":3,"away":0}},{"date":"19 Aug 2017","home":"Benevento","away":"Bologna FC","score":{"home":0,"away":1}}]"""

    assert(json === expected)
  }

}
