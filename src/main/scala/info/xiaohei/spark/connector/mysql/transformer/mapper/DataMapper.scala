package info.xiaohei.spark.connector.mysql.transformer.mapper

import java.sql.ResultSet

/**
  * Author: xiaohei
  * Date: 2017/4/16
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait DataMapper[T] extends Serializable {
  var index: Int = 1

  def map(resultSet: ResultSet): T
}

