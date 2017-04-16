package info.xiaohei.spark.connector.test

import info.xiaohei.spark.connector.RelationalDbEntry
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
    val entry = new RelationalDbEntry
    val res = entry.fromMysql[(Int, String, Int)]("test")
      .select("id", "name", "age")
      .get
    res.foreach(x => println(s"id:${x._1},name:${x._2},age:${x._3}"))

  }
}
