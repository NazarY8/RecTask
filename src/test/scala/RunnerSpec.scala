import cats.effect.unsafe.implicits.global
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RunnerSpec extends AnyFlatSpec with Matchers {

  "case when user provide divisor, http://localhost:8080/ws?divisor=2" should "return correct stream value" in {
    val config = AppConfig(2)
    val lines = Seq("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    val stream = Runner.calculateStreamValue(config, config.divisor, lines).compile.toList.unsafeRunSync()
    stream should contain theSameElementsAs Seq(
      "streamValue = 25"
    )
  }

  "case when user didn't provide divisor, use default app config with default divisor:5," +
    "http://localhost:8080/ws?divisor" should "return total information from stream" in {
    val config = AppConfig(5)
    val lines = Seq("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    val stream = Runner.calculateGeneralStreamInfo(config, lines).compile.toList.unsafeRunSync()
    stream shouldBe List("Key: 0, Value: 15" + "\n", "Key: 1, Value: 7" + "\n", "Key: 2, Value: 9" + "\n", "Key: 3, Value: 11" + "\n", "Key: 4, Value: 13" + "\n")
  }
}