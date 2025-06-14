# ChessGameETL
This project is a multi-module Scala ETL pipeline using Apache Spark to process chess game data. It reads raw CSV files, transforms and enriches the data (e.g., timestamps, ratings), and writes processed results. Modules include shared models, transformation logic, and ETL orchestration.
# ScalaSparkETL

## Overview

ScalaSparkETL is a multi-module ETL pipeline for processing chess game data using Apache Spark. It reads raw CSV files, transforms and enriches the data (e.g., timestamps, ratings), and writes processed results. The project is organized into three Maven modules:

- **etl-common**: Shared models and configuration utilities.
- **etl-transform**: Data transformation logic for chess games.
- **etl-job**: Orchestrates the ETL process.

## Features

- Batch processing of chess game data in CSV format.
- Timestamp conversion and feature engineering (duration, rating difference, etc.).
- Configurable via Typesafe Config.
- Modular, testable, and scalable Spark-based architecture.
## Project Structure  
ScalaSparkETL/ ├── etl-common/ │ └── src/main/scala/org/example/model/ChessGame.scala │ └── src/main/scala/org/example/config/ConfigReader.scala ├── etl-transform/ │ └── src/main/scala/org/example/transform/ChessGameTransformer.scala ├── etl-job/ │ └── src/main/scala/org/example/ChessGameETL.scala ├── src/main/resources/application.conf ├── pom.xml
## Prerequisites

- Java 8+
- Scala 2.12.x
- Apache Maven 3.x
- Apache Spark 3.3.x

## Configuration

Edit `src/main/resources/application.conf` to set input/output paths, Spark settings, and processing options.

## Build

From the project root, run:

```sh
mvn clean package
Run  
To execute the ETL job:
cd etl-job
mvn exec:java -Dexec.mainClass=org.example.ChessGameETL

License
This project is licensed under the MIT License.
```