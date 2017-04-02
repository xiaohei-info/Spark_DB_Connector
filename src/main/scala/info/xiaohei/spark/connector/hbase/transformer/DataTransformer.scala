package info.xiaohei.spark.connector.hbase.transformer

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
trait DataTransformer extends Serializable {
  type HBaseData = Iterable[Option[Array[Byte]]]

  //自定义class class时指定列名使用
  def columns: Iterable[String] = Iterable.empty
}
