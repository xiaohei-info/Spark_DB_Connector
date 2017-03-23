package info.xiaohei.spark.connector.test

import info.xiaohei.spark.connector.hbase._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Author: xiaohei
  * Date: 2017/3/23
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
      .set("spark.hbase.host", "ng5580b59-master-instance-5sgoh8iq.novalocal")

    val sc = new SparkContext(conf)
    val list = Seq[(String, String)](
      ("00001475304346643896037", "kgJkm0euSbe"),
      ("00001475376619355219953", "kiaR40qzI8o"),
      ("00001475458728618943637", "kgCoW0hgzXO"),
      ("00001475838363931738019", "kqiHu0WNJC0")

    )

    val rdd = sc.parallelize(list)

    rdd.toHBase("test")
      .insert("uid")
      .inColumnFamily("info")
      .save()

  }
}
