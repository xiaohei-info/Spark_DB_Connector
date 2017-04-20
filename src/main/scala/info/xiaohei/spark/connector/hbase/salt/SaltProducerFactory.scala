package info.xiaohei.spark.connector.hbase.salt

/**
  * Author: xiaohei
  * Date: 2017/4/19
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
class SaltProducerFactory[T] extends Serializable {
  def getHashProducer(saltArray: Iterable[T]): SaltProducer[T] = new HashSaltProducer[T](saltArray.toArray)

  def getRandomProducer(saltArray: Iterable[T]): SaltProducer[T] = new RandomSaltProducer[T](saltArray.toArray)
}
