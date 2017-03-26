package info.xiaohei.spark.connector.hbase.transformer.reader

import info.xiaohei.spark.connector.hbase.transformer.DataTransformer
import org.apache.hadoop.hbase.util.Bytes

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */

trait DataReader[T] extends DataTransformer {
  def transformHBaseData(data: HBaseData): T
}










