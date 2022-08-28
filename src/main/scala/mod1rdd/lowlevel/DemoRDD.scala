package mod1rdd.lowlevel

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object DemoRDD extends App {
  val spark: SparkSession = SparkSession
    .builder()
    .appName("Introduction to RDDs")
    .config("spark.master", "local")
    .getOrCreate()

  /**
   * Load data into RDD from parquet with an actual trip data file (src/main/resources/data/yellow_taxi_jan_25_2018).
   * Use lambda to build a table that shows what time the most calls occur.
   * Output the result to the screen and to a txt file with spaces.
   * Result: Data with the resulting table should appear in the console, a file should appear in the file system.
   * Check the solution in github gist.
   * */

  val sc: SparkContext = spark.sparkContext




}
