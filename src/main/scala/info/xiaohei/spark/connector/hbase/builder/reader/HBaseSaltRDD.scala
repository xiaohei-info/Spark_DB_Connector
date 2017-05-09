package info.xiaohei.spark.connector.hbase.builder.reader

import org.apache.spark.SparkContext
import org.apache.spark.rdd.{RDD, UnionRDD}

import scala.reflect.ClassTag

/**
  * Author: xiaohei
  * Date: 2017/4/25
  * Email: xiaohei.info@gmail.com
  * Host: xiaohei.info
  */
class HBaseSaltRDD[R: ClassTag](sc: SparkContext, rdds: Seq[RDD[R]]) extends UnionRDD[R](sc, rdds) {

}
