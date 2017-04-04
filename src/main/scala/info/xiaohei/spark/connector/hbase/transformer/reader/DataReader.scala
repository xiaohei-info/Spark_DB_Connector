package info.xiaohei.spark.connector.hbase.transformer.reader

import info.xiaohei.spark.connector.hbase.transformer.DataTransformer
import org.apache.hadoop.hbase.util.Bytes

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */

trait DataReader[T] extends DataTransformer {
  def read(data: HBaseData): T
}

trait SingleColumnDataReader[T] extends DataReader[T] {

  override def read(data: HBaseData): T =
    if (data.size == 1)
      columnMapWithOption(data.head)
    else if (data.size == 2)
      columnMapWithOption(data.drop(1).head)
    else
      throw new IllegalArgumentException(s"Unexpected number of columns: expected 1 or 2, returned ${data.size}")


  def columnMapWithOption(cols: Option[Array[Byte]]) =
    if (cols.nonEmpty) readSingleColumn(cols.get)
    else throw new IllegalArgumentException("Null value assigned to concrete class. Use Option[T] instead")

  def readSingleColumn(cols: Array[Byte]): T
}

trait TupleDataReader[T <: Product] extends DataReader[T] {

  val n: Int

  override def read(data: HBaseData): T =
    if (data.size == n)
      readTupleColumn(data)
    else if (data.size == n + 1)
      readTupleColumn(data.drop(1))
    else
      throw new IllegalArgumentException(s"Unexpected number of columns: expected $n or ${n - 1}, returned ${data.size}")

  def readTupleColumn(data: HBaseData): T
}

//
//trait SingleColumnDataReader[T] extends DataReader[T] {
//
//  override def read(data: HBaseData): T =
//    if (data.size == 1)
//      columnMapWithOption(data.head)
//    else if (data.size == 2)
//      columnMapWithOption(data.drop(1).head)
//    else
//      throw new IllegalArgumentException(s"Unexpected number of columns: expected 1 or 2, returned ${data.size}")
//
//  def columnMapWithOption(cols: Option[Array[Byte]]): T
//}









