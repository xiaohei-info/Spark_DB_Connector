package info.xiaohei.spark.connector.hbase.writer

import info.xiaohei.spark.connector.hbase.DataTransformer

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
trait DataWriter[T] extends DataTransformer{
  def convertHBaseData(data: T): HBaseData
}

trait SingleColumnDataWriter[T] extends DataWriter[T] {
  override def convertHBaseData(data: T): HBaseData = Seq(mapSingleColumn(data))

  def mapSingleColumn(data: T): Option[Array[Byte]]
}


