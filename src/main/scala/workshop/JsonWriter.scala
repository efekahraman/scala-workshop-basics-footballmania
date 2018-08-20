package workshop

import spray.json._
import DefaultJsonProtocol._

object JsonWriter {

  implicit val scoreJsonFormat: RootJsonFormat[Score] = jsonFormat2(Score)

  implicit val gameJsonFormat: RootJsonFormat[Game] = jsonFormat4(Game)

  def toJson(list: List[Game]): String = list.toJson.toString()

  def toPrettyJson(list: List[Game]): String = list.toJson.prettyPrint

  def toJson(error: Throwable): String = Map("errorMessage" -> error.getMessage).toJson.toString()

}
