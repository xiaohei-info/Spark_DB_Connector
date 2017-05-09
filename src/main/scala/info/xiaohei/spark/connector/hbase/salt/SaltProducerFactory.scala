package info.xiaohei.spark.connector.hbase.salt

import info.xiaohei.spark.connector.hbase.transformer.writer.DataWriter

import scala.reflect.ClassTag

/**
  * Author: xiaohei
  * Date: 2017/4/19
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
class SaltProducerFactory[T: ClassTag] extends Serializable {
  def getHashProducer(saltArray: Iterable[T])(implicit writer: DataWriter[T]): SaltProducer[T] = new HashSaltProducer[T](saltArray.toArray)

  def getRandomProducer(saltArray: Iterable[T])(implicit writer: DataWriter[T]): SaltProducer[T] = new RandomSaltProducer[T](saltArray.toArray)
}
