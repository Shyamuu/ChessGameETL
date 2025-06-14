// etl-transform/src/main/scala/org/example/transform/ChessGameTransformer.scala
package org.example.transform

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

class ChessGameTransformer {
  def transform(df: DataFrame): DataFrame = {
    // First convert the timestamp columns from unix timestamp (double) to proper timestamp
    val dfWithTimestamps = df
      .withColumn("created_at_timestamp",
        from_unixtime(col("created_at")).cast(TimestampType))
      .withColumn("last_move_at_timestamp",
        from_unixtime(col("last_move_at")).cast(TimestampType))

    //  calculate the duration and other transformations
    dfWithTimestamps
      .withColumn("game_duration",
        unix_timestamp(col("last_move_at_timestamp")) - unix_timestamp(col("created_at_timestamp")))
      .withColumn("rating_difference",
        abs(col("white_rating") - col("black_rating")))
      .withColumn("total_rating",
        col("white_rating") + col("black_rating"))
      .withColumn("processed_date", current_date())
      //  drop the intermediate timestamp columns
      .drop("created_at_timestamp", "last_move_at_timestamp")
  }
}
