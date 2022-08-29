package mod0scala

import io.circe
import io.circe.{Json, ParsingFailure}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._

import java.io.{FileOutputStream, PrintStream, PrintWriter}
import scala.language.implicitConversions

object mod0home extends App {
  /**
   * Parse the file with a (countries)[src/main/resources/countries/countries.json] via Scala/Java parse libs (not native Scala).
   * Find 5 asian countries with the smallest area.
   * Write the data to file as a json with the following structure:
   * [{“name”: <Official country name in English, string>,
   *  “capital”: <Name of the capital, line> (if there are several capitals, select the first one)),
   *  “area”: <Area of the country in square kilometers>}, ….
   *
   * */

  import model._

  using(scala.io.Source.fromFile("src/main/resources/countries/countries.json")) { src =>
    decode[List[Country]](src.mkString) match {
      case Right(value) =>
        value
          .filter(x => x.region.nonEmpty && x.region.get == "Africa")
          .sortBy(_.area)(Ordering[Double].reverse)
          .slice(0, 10)
          .map(CountryDto.createDto)
          .asJson
          .noSpaces
          .writeToFs("src/main/resources/countries/out.json")
      case Left(value) => throw new RuntimeException(s"Parsing problems: $value")
    }
  }

}

object model {
  case class Country(
                      name: Name,
                      capital: List[String],
                      area: Double,
                      region: Option[String])

  case class Name(common: String)

  case class CountryDto(
                         name: String,
                         capital: String,
                         area: Double
                       )

  object CountryDto {
    def createDto(country: Country): CountryDto =
      CountryDto(
        name = country.name.common,
        capital = country.capital.head,
        area = country.area
      )
  }

}

/**
 * implicit val countryDecoder: Decoder[Country] = (c: HCursor) => for {
 *    area <- c.downField("area").as[Double]
 *    name <- c.downField("name").downField("common").as[String]
 *    capital <- c.downField("capital").downN(0).as[Option[String]]
 *    region <- c.downField("region").as[String]
 * } yield Country(name, capital, area, region)
 *
 *
 * */

