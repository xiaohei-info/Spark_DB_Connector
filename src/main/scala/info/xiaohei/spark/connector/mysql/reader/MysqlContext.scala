package info.xiaohei.spark.connector.mysql.reader

import org.apache.spark.SparkContext

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
private[hbase] class MysqlContext(@transient sc: SparkContext) extends Serializable {
  def fromMysql(
                 connectStr: String,
                 username: String,
                 password: String
               ): MysqlReaderBuilder = new MysqlReaderBuilder(connectStr, username, password)
}

trait MysqlConextConversions extends Serializable {
  implicit def toMysqlContext(sc: SparkContext): MysqlContext = new MysqlContext(sc)
}
