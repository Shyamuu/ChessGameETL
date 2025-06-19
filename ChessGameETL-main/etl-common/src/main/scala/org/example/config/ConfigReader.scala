// etl-common/src/main/scala/org/example/config/ConfigReader.scala
package org.example.config

import com.typesafe.config.{Config, ConfigFactory}

object ConfigReader {
  private val config: Config = ConfigFactory.load()

  def getSparkMaster: String = config.getString("spark.master")

  def getInputPath: String = config.getString("paths.input.chess-games")

  def getOutputPath: String = config.getString("paths.output.processed-games")

  def getBatchSize: Int = config.getInt("processing.batch-size")

  def getDelimiter: String = config.getString("processing.delimiter")

  def getWriteMode: String = config.getString("processing.write-mode")

  def getSparkConf: Map[String, String] = {
    import scala.collection.JavaConverters._
    config.getConfig("spark.conf")
      .entrySet()
      .asScala
      .map(entry => (entry.getKey, entry.getValue.unwrapped().toString))
      .toMap
  }

  def getDateFormat: String = config.getString("processing.schema.date-format")

  def getTimestampColumns: List[String] = {
    import scala.collection.JavaConverters._
    config.getStringList("processing.schema.timestamp-columns").asScala.toList
  }
}
