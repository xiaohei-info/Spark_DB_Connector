package info.xiaohei.spark.connector.hbase.salt

import info.xiaohei.spark.connector.hbase.transformer.writer.{DataWriter, SingleColumnDataWriter}

import scala.reflect.ClassTag
import scala.util.Random

/**
  * Author: xiaohei
  * Date: 2017/4/19
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait SaltProducer[T] extends Serializable {

  def salting(rowkey: Array[Byte]): T

  protected def salts: Array[T]

  protected def verify(implicit writer: DataWriter[T]): Unit = {
    require(length > 0, "salt's length must great than 0")
  }

  def length(implicit writer: DataWriter[T]): Int = {
    require(writer.isInstanceOf[SingleColumnDataWriter[T]], "salt array must be composed with primitive type")

    val singleColumnDataWriter = writer.asInstanceOf[SingleColumnDataWriter[T]]
    salts.map(s => singleColumnDataWriter.writeSingleColumn(s))
      .map(b => b.getOrElse(Array[Byte]()))
      .map(_.length)
      .foldLeft(None.asInstanceOf[Option[Int]])((size1, size2) => {
        if (size1.nonEmpty && size1.get != size2) {
          throw new IllegalArgumentException(s"salts can not use different lengths with:${size1.get},$size2")
        }
        Some(size2)
      }).get
  }
}

private[salt] class RandomSaltProducer[T: ClassTag](val salts: Array[T])(implicit writer: DataWriter[T]) extends SaltProducer[T]() {

  verify

  override def salting(rowkey: Array[Byte]): T = {
    val randomizer = new Random
    salts(randomizer.nextInt(salts.length))
  }
}

private[salt] class HashSaltProducer[T: ClassTag](val salts: Array[T])(implicit writer: DataWriter[T]) extends SaltProducer[T]() {

  verify

  override def salting(rowkey: Array[Byte]): T = {
    salts((java.util.Arrays.hashCode(rowkey) & 0x7fffffff) % salts.length)
  }
}

//todo:test writer builder
//todo:add to collection
//todo:add to reader builder
//todo:ClassTag do what
