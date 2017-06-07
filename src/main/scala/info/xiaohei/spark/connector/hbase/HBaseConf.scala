package info.xiaohei.spark.connector.hbase

import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants}
import org.apache.spark.SparkConf

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */

case class HBaseConf private[hbase](hbaseHost: Option[String]
                                    , hbaseConfig: Option[String] = None
                                    , principal: Option[String] = None
                                    , keytab: Option[String] = None) {
  def createHadoopBaseConf() = {
    val conf = HBaseConfiguration.create()

    hbaseConfig.foreach {
      hbaseConfigValue =>
        for (localConf <- hbaseConfigValue.split(",")) {
          //todo:路径不存在的处理
          conf.addResource(new Path(localConf))
        }
    }

    //todo:测试两种读法
    //    val localConfigFile = Option(getClass.getClassLoader.getResource(hbaseConfig))
    //    localConfigFile.foreach(c => conf.addResource(c))

    hbaseHost.foreach {
      host =>
        conf.set(HConstants.ZOOKEEPER_QUORUM, host)
    }
    if (conf.get(HConstants.ZOOKEEPER_QUORUM).isEmpty) {
      conf.set(HConstants.ZOOKEEPER_QUORUM, HBaseConf.defaultHBaseHost)
    }

    principal.foreach {
      krb =>
        conf.set("spark.hbase.krb.principal", krb)
    }
    keytab.foreach {
      key =>
        conf.set("spark.hbase.krb.keytab", key)
    }

    conf
  }
}

object HBaseConf {

  val defaultHBaseHost = "localhost"

  def createFromSpark(conf: SparkConf) = {
    val hbaseHost = conf.get("spark.hbase.host", null)
    val hbaseConfig = conf.get("spark.hbase.config", null)

    val principal = conf.get("spark.hbase.krb.principal", null)
    val keytab = conf.get("spark.hbase.krb.keytab", null)
    HBaseConf(Option(hbaseHost), Option(hbaseConfig), Option(principal), Option(keytab))
  }

  def createConf(hbaseHost: String) = {
    HBaseConf(Option(hbaseHost))
  }
}
