package info.xiaohei.spark.connector.hbase.salt

import info.xiaohei.spark.connector.hbase.transformer.writer.{DataWriter, SingleColumnDataWriter}

/**
  * Author: xiaohei
  * Date: 2017/4/19
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
abstract class SaltProducer[T](saltArray: Iterable[T]) extends Serializable {

  def salt(rowkey: Array[Byte]): T

  def verify(implicit writer: DataWriter[T]): Unit = {
    require(writer.isInstanceOf[SingleColumnDataWriter[T]], "salt array must be composed with primitive type")

    val singleColumnDataWriter = writer.asInstanceOf[SingleColumnDataWriter[T]]
    saltArray.map(s => singleColumnDataWriter.writeSingleColumn(s))
      .map(b => b.getOrElse(Array[Byte]()))
      .map(_.length)
  }
}
