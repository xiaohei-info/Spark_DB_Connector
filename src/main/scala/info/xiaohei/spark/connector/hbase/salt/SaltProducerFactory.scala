package info.xiaohei.spark.connector.hbase.salt

/**
  * Author: xiaohei
  * Date: 2017/4/19
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
class SaltProducerFactory[T](saltArray: Iterable[T]) extends Serializable {
  def getHashProducer()

  def getRandomProducer()
}
