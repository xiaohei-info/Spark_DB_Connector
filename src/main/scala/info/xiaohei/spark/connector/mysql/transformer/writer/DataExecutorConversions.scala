package info.xiaohei.spark.connector.mysql.transformer.writer

import com.mysql.jdbc.PreparedStatement

/**
  * Author: xiaohei
  * Date: 2017/4/15
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait DataExecutorConversions extends Serializable {
  implicit def mysqlIntWriter: DataExecutor[Int] = new DataExecutor[Int] {
    override def write(data: Int): PreparedStatement = ???

    override protected val ps: PreparedStatement = _
  }
}
