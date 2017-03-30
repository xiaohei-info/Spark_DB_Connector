package info.xiaohei.spark.test

import org.apache.spark.{SparkConf, SparkContext}
import info.xiaohei.spark.connector.hbase._
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

/**
  * Author: xiaohei
  * Date: 2017/3/29
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
object Test {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.default.parallelism", "50")
      .set("spark.shuffle.manager", "hash")
      .set("spark.shuffle.consolidateFiles", "true")

    val sc = new SparkContext(conf)
    val hbaseRdd = sc.fromHBase[(String, String)]("city")
      .select("city", "date")
      .inColumnFamily("info")

    val rowRdd = hbaseRdd.map(x => Row(x._1, x._2))

    val sqlContext = new SQLContext(sc)
    val df = sqlContext.createDataFrame(rowRdd, CitySchema.struct)
    
    df.show()
    df.registerTempTable("city")
    sqlContext.sql("select date from city").show()

  }

  object CitySchema {
    val city = StructField("city", StringType)
    val date = StructField("date", StringType)
    val struct = StructType(Array(city, date))
  }

}
