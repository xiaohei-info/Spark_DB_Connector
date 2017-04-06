package info.xiaohei.spark.connector.mysql.reader

import org.apache.spark.SparkContext

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
private[mysql] class MysqlContext(@transient sc: SparkContext) extends Serializable {
  def fromMysql(tableName: String): MysqlReaderBuilder = new MysqlReaderBuilder(tableName)
}

trait MysqlConextConversions extends Serializable {
  implicit def toMysqlContext(sc: SparkContext): MysqlContext = new MysqlContext(sc)
}
