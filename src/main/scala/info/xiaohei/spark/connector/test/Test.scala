package info.xiaohei.spark.connector.test

import info.xiaohei.spark.connector.hbase._
import info.xiaohei.spark.connector.mysql.MysqlConf
import info.xiaohei.spark.connector.mysql._

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
    implicit val hBaseConf = HBaseConf.createConf("localhost")

    val list = Seq[(String, Int)](
      ("xiaohei", 24)
    )
    list.toMysql("test")
      .insert("name", "age")
      .save()
  }
}
