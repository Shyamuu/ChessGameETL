// etl-transform/src/test/scala/org/example/transform/ChessGameTransformerTest.scala
package org.example.transform

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.funsuite.AnyFunSuite
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

class ChessGameTransformerTest extends AnyFunSuite {

  lazy val spark: SparkSession = SparkSession
    .builder()
    .appName("ChessGameTransformerTest")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._

  test("transform should correctly calculate game duration and ratings") {
    // Arrange
    val transformer = new ChessGameTransformer()

    val inputData = Seq(
      (1609459200.0, 1609462800.0, 1500, 1600), // 1 hour difference
      (1609459200.0, 1609459500.0, 2000, 1800)  // 5 minutes difference
    ).toDF("created_at", "last_move_at", "white_rating", "black_rating")

    // Act
    val result = transformer.transform(inputData)

    // Assert
    assert(result.columns.contains("game_duration"))
    assert(result.columns.contains("rating_difference"))
    assert(result.columns.contains("total_rating"))
    assert(result.columns.contains("processed_date"))

    val rows = result.collect()

    // First game
    assert(rows(0).getAs[Long]("game_duration") === 3600) // 1 hour = 3600 seconds
    assert(rows(0).getAs[Int]("rating_difference") === 100)
    assert(rows(0).getAs[Long]("total_rating") === 3100)

    // Second game
    assert(rows(1).getAs[Long]("game_duration") === 300) // 5 minutes = 300 seconds
    assert(rows(1).getAs[Int]("rating_difference") === 200)
    assert(rows(1).getAs[Long]("total_rating") === 3800)
  }

  test("transform should handle null values gracefully") {
    // Arrange
    val transformer = new ChessGameTransformer()

    val inputData = Seq(
      (null, 1609462800.0, 1500, 1600),
      (1609459200.0, null, 2000, 1800)
    ).toDF("created_at", "last_move_at", "white_rating", "black_rating")

    // Act
    val result = transformer.transform(inputData)

    // Assert
    val rows = result.collect()
    assert(rows(0).getAs[Long]("game_duration").isNaN)
    assert(rows(1).getAs[Long]("game_duration").isNaN)
  }

  test("transform should verify column types") {
    // Arrange
    val transformer = new ChessGameTransformer()

    val inputData = Seq(
      (1609459200.0, 1609462800.0, 1500, 1600)
    ).toDF("created_at", "last_move_at", "white_rating", "black_rating")

    // Act
    val result = transformer.transform(inputData)

    // Assert
    val schema = result.schema
    assert(schema("game_duration").dataType === LongType)
    assert(schema("rating_difference").dataType === IntegerType)
    assert(schema("total_rating").dataType === LongType)
    assert(schema("processed_date").dataType === DateType)
  }
}
