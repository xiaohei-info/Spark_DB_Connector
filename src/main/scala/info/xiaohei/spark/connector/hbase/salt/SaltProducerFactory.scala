package info.xiaohei.spark.connector.hbase.salt

import scala.reflect.ClassTag

/**
  * Author: xiaohei
  * Date: 2017/4/19
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
class SaltProducerFactory[T: ClassTag] extends Serializable {
  def getHashProducer(saltArray: Iterable[T]): SaltProducer[T] = new HashSaltProducer[T](saltArray.toArray)

  def getRandomProducer(saltArray: Iterable[T]): SaltProducer[T] = new RandomSaltProducer[T](saltArray.toArray)
}
