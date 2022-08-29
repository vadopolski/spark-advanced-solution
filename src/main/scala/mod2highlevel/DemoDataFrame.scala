package mod2highlevel

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.functions.{broadcast, col}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.storage.StorageLevel.DISK_ONLY
import org.apache.spark.sql.functions.{col, count, desc}


object DemoDataFrame extends App {

  /**
   * Create first DataFrame from the Parquet actual trip data file (src/main/resources/data/yellow_taxi_jan_25_2018).
   * Load data into a second DataFrame from a csv trip reference file (src/main/resources/data/taxi_zones.csv).
   * Using DSL and two this dataframes build a table that will show which areas are the most popular for bookings.
   * Display the result on the screen and write it to the Parquet file.
   *
   * Result: Data with the resulting table should appear in the console, a file should appear in the file system.
   *
   * */

  val slotsNumber = 2

  implicit val spark = SparkSession
    .builder()
    .appName("Introduction to RDDs")
    .config("spark.master", s"local[$slotsNumber]")
    .getOrCreate()


  val taxiFactsDF = spark
    .read
    .load("src/main/resources/yellow_taxi_jan_25_2018")
    .repartition(slotsNumber)

  taxiFactsDF.cache()

  val taxiZonesDF = spark
    .read
    .format("csv")
    .option("header", "true")
    .load("src/main/resources/taxi_zones.csv")

  val mostPopularDF = taxiFactsDF.as("facts")
    .join(taxiZonesDF.as("zones"), col("facts.DOLocationID") === col("zones.LocationID"))
    .select("LocationID", "Zone")
    .groupBy("LocationID", "Zone")
    .agg(count(col("LocationID")).as("Count"))
    .orderBy(desc("Count"))
    .coalesce(1)

  mostPopularDF.show(5)

  mostPopularDF.write.save("mostPopular_taxi_dest_25_2018")
}
