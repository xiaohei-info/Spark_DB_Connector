package info.xiaohei.spark.connector.test

import info.xiaohei.spark.connector.RelationalDbEntry
import info.xiaohei.spark.connector.hbase.salt.SaltProducerFactory
import info.xiaohei.spark.connector.mysql._
import info.xiaohei.spark.connector.mysql.transformer.executor.{CustomDataExecutor, DataExecutor}
import info.xiaohei.spark.connector.mysql.transformer.mapper.{CustomDataMapper, DataMapper}
import org.apache.hadoop.hbase.util.Bytes
import info.xiaohei.spark.connector.hbase._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Author: xiaohei
  * Date: 2017/4/6
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */


object Test {
  def main(args: Array[String]) {

    implicit val mysqlConf = MysqlConf.createConf(
      "127.0.0.1",
      "root",
      "root",
      "3306",
      "its"
    )
    val conf = new SparkConf().setAppName("")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.default.parallelism", "50")
      .set("spark.shuffle.manager", "hash")
      .set("spark.shuffle.consolidateFiles", "true")

    val sc = new SparkContext(conf)
    val list = (1 to 1000).map {
      x =>
        var res = ""
        if (x < 10) {
          res = s"000$x"
        } else if (x < 100) {
          res = s"00$x"
        } else if (x < 1000) {
          res = s"0$x"
        } else {
          res = x.toString
        }
        (res, "Avalue")
    }
    implicit val hBaseConf = HBaseConf.createFromSpark(conf)
    list.toHBase("salt_test")
      .insert("value")
      .inColumnFamily("info")
      .withSalt((0 to 9).map(s => s.toString))
      .save()
    //    sc.parallelize(list)
    //      .toHBase("salt_test")
    //      .insert("value")
    //      .inColumnFamily("info")
    //      .withSalt((0 to 9).map(s => s.toString))
    //      .save()


    //    val rdd = sc.fromHBase[(String, String)]("salt_test")
    //      .select("value")
    //      .inColumnFamily("info")
    //      .withSalt((0 to 9).map(s => s.toString))
    //    rdd.collect().foreach(x => println(s"x-->$x"))

  }


}
