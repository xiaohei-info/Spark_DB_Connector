package info.xiaohei.spark.connector.hbase

import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants}
import org.apache.spark.SparkConf

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */

case class HBaseConf private[hbase](hbaseHost: Option[String] = None
                                    , hbaseConfig: String = "hbase-site.xml") {
  def createHadoopBaseConf() = {
    val conf = HBaseConfiguration.create()

    val localConfigFile = Option(getClass.getClassLoader.getResource(hbaseConfig))
    localConfigFile.foreach(c => conf.addResource(c))

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

  def createFromSpark(conf: SparkConf) = {
    val hbaseHost = conf.get("spark.hbase.host", null)
    val hbaseConfig = conf.get("spark.hbase.config", "hbase-site.xml")
    HBaseConf(Option(hbaseHost), hbaseConfig)
  }

  def createConf(hbaseHost: String) = {
    HBaseConf(Option(hbaseHost))
  }
}
