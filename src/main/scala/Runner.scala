import pureconfig._
import pureconfig.generic.auto._

import scala.io.Source
import cats.effect.{ExitCode, IO, IOApp}
import fs2.Stream
import org.http4s.HttpRoutes
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.io.OptionalQueryParamDecoderMatcher

object Runner extends IOApp {

  def readCsvFile(): Seq[String] = {
    val fileName = "file.csv"
    val file = Source.fromFile(fileName)
    val lines = file.getLines().toSeq
    lines
  }

  def getStreamValuesFromCsv(config: AppConfig, lines: Seq[String]): Map[Int, Int] = {
    //split lines to stream
    val streams: Map[Int, Seq[String]] = lines.groupBy(line => line.toInt % config.divisor)

    // ADDING NUMBERS FOR EACH STREAMS
    val streamsValues: Map[Int, Int] = streams.map { case (k, v) =>
      k -> v.foldLeft(0) { (acc, s) => acc + s.toInt }
    }
    streamsValues
  }

  def calculateGeneralStreamInfo(config: AppConfig, lines: Seq[String]): Stream[IO, String] = {
    val streamsValues: Map[Int, Int] = getStreamValuesFromCsv(config, lines)
    Stream.emits(streamsValues.toList)
      .map { case (key, value) =>
        s"Key: $key, Value: $value" + "\n"
      }
  }

  def calculateStreamValue(config: AppConfig, key: Int, lines: Seq[String]): Stream[IO, String] = {
    val streamsValues = getStreamValuesFromCsv(config, lines)
    println(s"streamValue with custom config: $streamsValues")
    val value = streamsValues.get(key - 1)
    Stream.emit(s"streamValue = ${value.getOrElse(throw new IndexOutOfBoundsException("Error"))}")
  }

  def run(args: List[String]): IO[ExitCode] = {
    //read csv file and calculate lines
    val lines: Seq[String] = readCsvFile()
    val numberOfLines = lines.size
    //get default divisor
    val config = ConfigSource.file("config.yaml").loadOrThrow[AppConfig]
    println(s"Default divisor from config file: ${config.divisor}")

    val dsl = Http4sDsl[IO]
    import dsl._

    val streamRoute = HttpRoutes.of[IO] {
      case GET -> Root / "ws" :? DivisorQueryParamMatcher(divisor) =>
        if (divisor.forall(_ <= numberOfLines)) {
          val customConfig = AppConfig(divisor.getOrElse(0))
          customConfig.divisor match {
            case 0 => Ok(calculateGeneralStreamInfo(config, lines)) // case for http://localhost:8080/ws?divisor when someone didn't provide divisor
            case _ => Ok(calculateStreamValue(customConfig, customConfig.divisor, lines))
          }
        } else {
          BadRequest(s"Divisor must be $numberOfLines or less. By default .csv file have only $numberOfLines rows.")
        }
    }.orNotFound

    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(streamRoute)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}


case class AppConfig(divisor: Int)

object DivisorQueryParamMatcher extends OptionalQueryParamDecoderMatcher[Int]("divisor")
