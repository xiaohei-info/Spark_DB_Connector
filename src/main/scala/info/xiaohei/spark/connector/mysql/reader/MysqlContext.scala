package info.xiaohei.spark.connector.mysql.reader

import org.apache.spark.SparkContext

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
private[mysql] class MysqlContext(@transient sc: SparkContext) extends Serializable {
  def fromMysql(): MysqlReaderBuilder = new MysqlReaderBuilder()
}

trait MysqlConextConversions extends Serializable {
  implicit def toMysqlContext(sc: SparkContext): MysqlContext = new MysqlContext(sc)
}
