package info.xiaohei.spark.connector.hbase.writer

import info.xiaohei.spark.connector.hbase.FieldMapper

/**
  * Created by xiaohei on 2017/3/20.
  */
trait FieldWriter[T] extends FieldMapper{
  def convertHBaseData(data: T): HBaseData
}

trait SingleColumnFieldWriter[T] extends FieldWriter[T] {
  override def convertHBaseData(data: T): HBaseData = Seq(mapSingleColumn(data))

  def mapSingleColumn(data: T): Option[Array[Byte]]
}


