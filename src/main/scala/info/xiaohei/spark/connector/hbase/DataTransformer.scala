package info.xiaohei.spark.connector.hbase

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
trait DataTransformer extends Serializable{
  type HBaseData = Iterable[Option[Array[Byte]]]
}
