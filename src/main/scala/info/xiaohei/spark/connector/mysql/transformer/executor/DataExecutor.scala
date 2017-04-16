package info.xiaohei.spark.connector.mysql.transformer.executor

import java.sql.PreparedStatement


/**
  * Author: xiaohei
  * Date: 2017/4/15
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait DataExecutor[T] extends Serializable {
  var index: Int = 1

  def prepare(ps: PreparedStatement, data: T): Unit

  //todo:ps传递方式
  def execute(ps: PreparedStatement, data: T): Unit = {
    prepare(ps, data)
    ps.executeUpdate()
  }
}

abstract class CustomDataExecutor[S, T](implicit dataExecutor: DataExecutor[T]) extends DataExecutor[S] {

  override def prepare(ps: PreparedStatement, data: S) = dataExecutor.prepare(ps, convert(data))

  def convert(data: S): T
}
