package info.xiaohei.spark.connector.test

import info.xiaohei.spark.connector.mysql.MysqlConf

/**
  * Author: xiaohei
  * Date: 2017/4/6
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
object Test {
  def main(args: Array[String]) {
    val mysqlConf = MysqlConf.createConf()
    mysqlConf.set("","")

  }
}
