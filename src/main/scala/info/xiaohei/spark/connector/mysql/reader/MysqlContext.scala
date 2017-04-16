package info.xiaohei.spark.connector.mysql.reader

import info.xiaohei.spark.connector.RelationalDbEntry
import org.apache.spark.SparkContext

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
private[mysql] class MysqlContext() extends Serializable {
  def fromMysql[T](tableName: String): MysqlReaderBuilder[T] = new MysqlReaderBuilder[T](tableName)
}

trait MysqlContextConversions extends Serializable {
  implicit def scToMysqlContext(sc: SparkContext): MysqlContext = new MysqlContext()

  implicit def entryToMysqlContext(entry: RelationalDbEntry): MysqlContext = new MysqlContext()
}
