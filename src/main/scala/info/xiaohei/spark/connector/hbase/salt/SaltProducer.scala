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
    val saltLength = saltArray.map(s => singleColumnDataWriter.writeSingleColumn(s))
      .map(b => b.getOrElse(Array[Byte]()))
      .map(_.length)
      //todo:优化
      .foldLeft(None.asInstanceOf[Option[Int]])((size1, size2) => {
      if (size1.nonEmpty && size1.get != size2) {
        throw new IllegalArgumentException(s"salts can not use different lengths with:${size1.get},$size2")
      }
      Some(size2)
    }).get
    require(saltLength > 0, "salt's length must great than 0")
  }
}
