1. Module 0 - Scala in one day
    1. Meet with Scala features which is used in Spark framework
    2. Using libs for parsing JSON write code [issue_1](src/main/scala/mod0scala/mod0home.scala)
    3. Theory
        1. var and val, val (x, x), lazy val, [transient lazy val](http://fdahms.com/2015/10/14/scala-and-the-transient-lazy-val-pattern/)
        2. type and Type, (Nil, None, Null => null, Nothing, Unit => (), Any, AnyRef, AnyVal, String, interpolation
        3. class, object (case), abstract class, trait
        4. Scala function, methods, lambda
        5. Generic, ClassTag, covariant, contravariant, invariant position, F[_], *
        6. Pattern matching and if then else construction
        7. Mutable and Immutable collection, Iterator, collection operation - https://superruzafa.github.io/visual-scala-reference/
        8. Monads (Option, Either, Try, Future, ....), Try().recovery
        9. map, flatMap, foreach, for comprehension
        10. Implicits, private[sql], package
        11. Scala sbt, assembly
        12. Encoder, Product
        13. Scala libs:
2. Module 1 - RDD
    1. [Exercise find popular time for orders](src/main/scala/mod1rdd/lowlevel/DemoRDD.scala)
    2. Theory RDD api:
        1. RDD creating api: from array, from file. from DS
        2. RDD base operations: map, flatMap, filter, reduceByKey, sort
        3. Time parse libs
    3. [Exercise find dependencies](src/main/scala/mod1rdd/lowlevel/FindDependencies.scala)
    4. [Exercise make join without shuffle](src/main/scala/mod1rdd/lowlevel/Join.scala)
    5. Theory RDD under the hood:
        1. Iterator + mapPartitions()
        2. RDD creating path: compute() and getPartitions()
        3. Partitions
        4. Partitioner: Hash and Range
        5. Dependencies: wide and narrow
        6. Joins: inner, cogroup, join without shuffle
        7. Query Plan
3. Module 2 - DataFrame & DataSet, Spark DSL & Spark SQL
    1. [Exercise Find most popular boroughs for orders](src/main/scala/ch3batch/highlevel/DemoDataFrame.scala)
    2. [Find distance distribution for orders grouped by boroughs](src/main/scala/ch3batch/highlevel/DemoDataSet.scala)
    3. Theory DataFrame, DataSet api:
        1. Creating DataFrame: memory, from file (HDFS, S3, FS) (Avro, Orc, Parquet)
        2. Spark DSL: Join broadcast, Grouped operations
        3. Spark SQL: Window functions, single partitions,
        4. Scala UDF Problem-solving
        5. Spark catalog, ....,
    4. Recreate code using plans [Reverse engineering](src/main/scala/ch3batch/highlevel/dataframe.scala)
    5. Theory
        1. Catalyst Optimiser: Logical & Physical plans
        2. Codegen
        3. Persist vs Cache vs Checkpoint
        4. Creating DataFrame Path
        5. Raw vs InternalRaw
4. Module 3 - Spark optimisation
    1. [Compare speed, size RDD, DataFrame, DataSet](src/main/scala/ch3batch/highlevel/DataFrameVsRDD.scala)
    2. Compare crimes counting: SortMerge Join, BroadCast, BlumFilter
    3. Resolve problem with a skew join
    4. Build UDF for Python and Scala
    5. UDF Problems
5. Module 4 - External and Connectors
    1. [Write custom connectors](src/main/scala/org/example/datasource/postgres/PostgresDatasource.scala)
    2. Theory
        1. How to read/write data from file storages (HDFS, S3, FTP, FS)?
        2. What data format to choose (Json, CSV, Avro, Orc, Parquet, Delta, ... )?
        3. How to parallelize reading/writing to JDBC?
        4. How to create dataframe from MPP (Cassandra, vertica, gp)
        5. How to work with Kafka?
        6. How to write your own connectors?
        7. Write UDF for joining with cassandra
6. Module 5 - Testing
    1. Write test for data marts written in module (Exercise find popular time for orders, Find most popular boroughs for orders, Find distance distribution for orders grouped by boroughs)
    2. Theory
        1. Unit testing
        2. Code review
        3. QA
        4. CI/CD
        5. Problems
        6. Libs which solve this problems
7. Module 6 - Spark Cluster
    1. Build config with allocation
    2. Compare several workers
    3. Dynamic Resource Allocation
    4. Manual managing executors runtime
8. Module 7 - Spark streaming
    1. [Solve problem with Cassandra writing](src/main/scala/mod4connectors/DataSetsWithCassandra.scala)
    2. Build Spark Structure Reading Kafka
    3. Build Spark Structure Using State
    4. Build Spark Structure Writing Cassandra






Labs Spark Advanced (find place in Spark)
1. Write two class which convert JSON String to InternalRaw and InternalRaw to JSON String (theory about UDF how to write Internal RAW + DataSet method to_json)
2. Write static data source
3. Add predicate push down to data source API
4. Add structure streaming API support to data source API

Modified homework:
1. Write function - def toJSON(Iterator[InternalRaw]): Iterator[String] (example find in Spark - DataSet, json method)
2. 