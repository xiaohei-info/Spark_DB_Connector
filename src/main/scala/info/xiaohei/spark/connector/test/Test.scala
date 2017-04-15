package info.xiaohei.spark.connector.test

import info.xiaohei.spark.connector.hbase._
import info.xiaohei.spark.connector.mysql.MysqlConf

/**
  * Author: xiaohei
  * Date: 2017/4/6
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
object Test {
  def main(args: Array[String]) {

    implicit val mysqlConf = MysqlConf.createConf(
      "your-host",
      "username",
      "password",
      "port",
      "db-name"
    )
    implicit val hBaseConf = HBaseConf.createConf("localhost")

    val list = Seq[String]("")
    list.toHBase("")
      .insert("")
      .inColumnFamily("")
  }
}
