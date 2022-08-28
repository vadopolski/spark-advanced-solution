package mod2highlevel

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.functions.{broadcast, col}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.storage.StorageLevel.DISK_ONLY

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

  implicit val spark = SparkSession
    .builder()
    .appName("Introduction to RDDs")
    .config("spark.master", "local")
    .getOrCreate()


}
