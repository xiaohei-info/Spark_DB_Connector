package info.xiaohei.spark.connector.transformer.reader

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait SingleColumnConcreteDataReader[T] extends SingleColumnDataReader[T] {

  def columnMapWithOption(cols: Option[Array[Byte]]) =
    if (cols.nonEmpty) columnMap(cols.get)
    else throw new IllegalArgumentException("Null value assigned to concrete class. Use Option[T] instead")

  def columnMap(cols: Array[Byte]): T
}
