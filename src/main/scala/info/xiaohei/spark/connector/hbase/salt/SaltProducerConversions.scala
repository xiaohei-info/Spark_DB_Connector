package info.xiaohei.spark.connector.hbase.salt

import info.xiaohei.spark.connector.hbase.transformer.writer.DataWriter

import scala.reflect.ClassTag

/**
  * Author: xiaohei
  * Date: 2017/4/20
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait SaltProducerConversions extends Serializable {
  implicit def getSaltProducerFactory[T: ClassTag](implicit writer: DataWriter[T]): SaltProducerFactory[T] = new SaltProducerFactory[T]()
}
