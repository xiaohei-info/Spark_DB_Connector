package info.xiaohei.spark.connector.hbase.transformer.reader

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait SingleColumnDataReader[T] extends DataReader[T] {

  def read(data: HBaseData): T =
    if (data.size == 1)
      columnMapWithOption(data.head)
    else if (data.size == 2)
      columnMapWithOption(data.drop(1).head)
    else
      throw new IllegalArgumentException(s"Unexpected number of columns: expected 1 or 2, returned ${data.size}")

  def columnMapWithOption(cols: Option[Array[Byte]]): T
}
