// etl-job/src/main/scala/org/example/ChessGameETL.scala
package org.example

import org.apache.spark.sql.SparkSession
import org.example.transform.ChessGameTransformer

object ChessGameETL {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Chess Game ETL")
      .master("local[*]")
      .getOrCreate()

    // Read input CSV
    val inputDF = spark.read
      .option("header", "true")
      .option("delimiter", ",")
      .option("inferSchema", "true")
      .csv("src/main/resources/input/sample_data.csv")

    // Apply transformations
    val transformer = new ChessGameTransformer
    val transformedDF = transformer.transform(inputDF)

    // Write output
    transformedDF.write
      .mode("overwrite")
      .option("header", "true")
      .csv("src/main/resources/output/processed_chess_games")

    spark.stop()
  }
}
