package info.xiaohei.spark.connector.mysql.transformer.writer

import com.mysql.jdbc.PreparedStatement

/**
  * Author: xiaohei
  * Date: 2017/4/15
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait DataExecutor[T] extends Serializable {
  var ps: PreparedStatement = _

  protected var index: Int

  def prepare(data: T): PreparedStatement

  def execute(data: T): Unit = {
    prepare(data)
    ps.executeUpdate()
  }
}

//trait SingleColumnDataWriter[T] extends DataWriter[T] {
//  override def write(data: T) = withSingleColumn(data)
//
//  def withSingleColumn(data: T): PreparedStatement
//}
