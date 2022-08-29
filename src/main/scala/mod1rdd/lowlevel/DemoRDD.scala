package mod1rdd.lowlevel

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import java.util.Calendar
import java.sql.Timestamp

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

  val taxiFactsDF = spark
    .read
    .load("src/main/resources/yellow_taxi_jan_25_2018")

  val mostPopularHoursRDD = taxiFactsDF.rdd.map(row => {
    val cal = Calendar.getInstance()
    cal.setTime(row.getAs[Timestamp]("tpep_pickup_datetime"))
    (cal.get(Calendar.HOUR_OF_DAY), 1)
  })
    .reduceByKey(_ + _)
    .map(row => (row._2, row._1))
    .sortByKey(ascending = false)
    .map(row => (row._2, row._1))

  val rddWithHeader = spark.sparkContext.parallelize(Seq(("HOUR", "COUNT")))
    .union(mostPopularHoursRDD.map(row => (row._1.toString, row._2.toString)))
    .coalesce(1)

  rddWithHeader
    .collect()
    .foreach(row => println(row._1 + "\t" + row._2))

  rddWithHeader
    .map(row => row._1 + " " + row._2)
    .saveAsTextFile("most_popular_hours.txt")

}