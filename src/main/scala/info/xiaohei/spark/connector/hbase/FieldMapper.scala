package info.xiaohei.spark.connector.hbase

/**
  * Created by xiaohei on 2017/3/20.
  */
trait FieldMapper extends Serializable{
  type HBaseData = Iterable[Option[Array[Byte]]]
}
