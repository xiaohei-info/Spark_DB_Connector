package info.xiaohei.spark.connector.hbase.reader

import org.apache.spark.SparkContext

import scala.reflect.ClassTag

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
class HBaseContext(@transient sc: SparkContext) extends Serializable {
  def fromHBase[R: ClassTag](tableName: String): HBaseReaderBuilder[R] = new HBaseReaderBuilder[R](sc, tableName = tableName)
}

trait HBaseContextConversions extends Serializable {
  implicit def toHBaseContext(sc: SparkContext): HBaseContext = new HBaseContext(sc)
}
