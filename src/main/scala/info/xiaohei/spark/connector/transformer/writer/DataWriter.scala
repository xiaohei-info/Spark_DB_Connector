package info.xiaohei.spark.connector.transformer.writer

import info.xiaohei.spark.connector.transformer.DataTransformer

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
trait DataWriter[T] extends DataTransformer{
  def convertHBaseData(data: T): HBaseData
}



