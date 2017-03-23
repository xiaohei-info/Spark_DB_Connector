package info.xiaohei.spark.connector.hbase

import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants}
import org.apache.spark.SparkConf

/**
  * Created by xiaohei on 2017/3/20.
  */

//todo:读取本地hbase-site.xml文件内容
private[hbase] case class HBaseConf(hbaseHost: Option[String] = None) {
  def createHadoopBaseConf() = {
    val conf = HBaseConfiguration.create()
    hbaseHost.foreach {
      host =>
        conf.set(HConstants.ZOOKEEPER_QUORUM, host)
    }
    if (conf.get(HConstants.ZOOKEEPER_QUORUM).isEmpty) {
      conf.set(HConstants.ZOOKEEPER_QUORUM, HBaseConf.defaultHBaseHost)
    }
    conf
  }
}

object HBaseConf {

  val defaultHBaseHost = "localhost"

  def fromSpark(conf: SparkConf) = {
    val hbaseHost = conf.get("spark.hbase.host", null)
    HBaseConf(Option(hbaseHost))
  }
}
