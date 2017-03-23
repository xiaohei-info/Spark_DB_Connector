package info.xiaohei.spark.connector.hbase.reader

import org.apache.spark.SparkContext

import scala.reflect.ClassTag

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email yuande.jiang@fugetech.com
  * Last Modified by: xiaohei
  * Last Modified time: 2017/3/21
  */
class HBaseContext(@transient sc: SparkContext) extends Serializable {
  def hbaseTable[R: ClassTag](tableName: String): HBaseReaderBuilder[R] = new HBaseReaderBuilder[R](sc, tableName = tableName)
}

trait HBaseContextConversions extends Serializable {
  implicit def toHBaseContext(sc: SparkContext): HBaseContext = new HBaseContext(sc)
}
