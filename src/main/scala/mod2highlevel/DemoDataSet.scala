package mod2highlevel

import org.apache.spark.sql.functions.{broadcast, col}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SaveMode, SparkSession}
import org.apache.spark.sql.functions._

import java.util.Properties

object DemoDataSet extends App {
  /**
   * Create the first DataSet from the actual trip data file in Parquet (src/main/resources/data/yellow_taxi_jan_25_2018).
   * Using DSL and lambda, build a table that will show - How is the distribution of trips by distance?
   * Display the result on the screen and write it to the Postgres database (docker in the project).
   * To write to the database, you need to think over and also attach an init sql file with a structure.
   *
   * (Example: you can build a storefront with the following columns: total trips, average distance, standard deviation,
   * minimum and maximum distance)
   *
   * Result: Data with the resulting table should appear in the console, a table should appear in the database.
   * Check out the solution in github gist.
   *
   * */

  import mod2highlevel.model._

  val spark = SparkSession
    .builder()
    .appName("Introduction to RDDs")
    .config("spark.master", "local[2]")
    .config("spark.sql.codegen.comments", "true")
    .getOrCreate()

  val taxiFactsDF = spark
    .read
    .load("src/main/resources/yellow_taxi_jan_25_2018")

  val aggDF = taxiFactsDF
    .withColumn("calendar_date", to_date(col("tpep_pickup_datetime"), "yyyy-MM-dd"))
    .groupBy("calendar_date")
    .agg(
      count("trip_distance").as("total_trip_count"),
      avg("trip_distance").as("mean_distance"),
      stddev("trip_distance").as("stddev"),
      min("trip_distance").as("min_distance"),
      max("trip_distance").as("max_distance")
    )
    .select(
      "calendar_date",
      "total_trip_count",
      "mean_distance",
      "stddev",
      "min_distance",
      "max_distance"
    )

  //  aggDF.show()

  val driver = "org.postgresql.Driver"
  val url = "jdbc:postgresql://localhost:5432/home"
  val user = "docker"
  val password = "docker"



  val props = new Properties()
  props.put("user", user)
  props.put("password", password)

  aggDF
    .write
    .option("driver", driver)
    .mode(SaveMode.Overwrite)
    .jdbc(url, "distance_distribution", props)



  spark.sqlContext.read
    .format("jdbc")
    .option("url", url)
    .option("dbtable", "distance_distribution")
    .option("user", user)
    .option("password", password)
    .load()
    .show()


}
