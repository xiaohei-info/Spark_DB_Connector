package info.xiaohei.spark.connector.hbase.transformer.writer

import info.xiaohei.spark.connector.hbase.transformer.DataTransformer

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
trait DataWriter[T] extends DataTransformer {
  def write(data: T): HBaseData
}

trait SingleColumnDataWriter[T] extends DataWriter[T] {
  override def write(data: T): HBaseData = Seq(writeSingleColumn(data))

  def writeSingleColumn(data: T): Option[Array[Byte]]
}

abstract class CustomDataWriter[S, T](implicit writer: DataWriter[T]) extends DataWriter[S] {

  override def write(data: S): HBaseData = writer.write(convert(data))

  def convert(data: S): T
}




