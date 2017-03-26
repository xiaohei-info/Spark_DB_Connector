package info.xiaohei.spark.connector.hbase.transformer.writer

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait SingleColumnDataWriter[T] extends DataWriter[T] {
  override def convertHBaseData(data: T): HBaseData = Seq(mapSingleColumn(data))

  def mapSingleColumn(data: T): Option[Array[Byte]]
}
