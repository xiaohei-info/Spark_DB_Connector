package info.xiaohei.spark.connector

import org.apache.spark.SparkContext

/**
  * Author: xiaohei
  * Date: 2017/4/6
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */

object ConfOption extends Enumeration {
  type ConfOption = Value
  val SPARK_HBASE_HOST = Value("spark.hbase.host")
  val SPARK_MYSQL_HOST = Value("spark.mysql.host")
  val SPARK_MYSQL_USERNAME = Value("spark.mysql.username")
  val SPARK_MYSQL_PASSWORD = Value("spark.mysql.password")
  val SPARK_MYSQL_PORT = Value("spark.mysql.port")
  val SPARK_MYSQL_DB = Value("spark.mysql.db")
}

case class CollectionConf private[connector](conf: Map[String, String] = Map.empty)

object CollectionConf {
  def createCollectionConf(sc: SparkContext) = {
    val sparkConf = sc.getConf
    val collectionConf = Map[String, String](
      ConfOption.SPARK_HBASE_HOST.toString -> sparkConf.get(ConfOption.SPARK_HBASE_HOST.toString),
      ConfOption.SPARK_MYSQL_HOST.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_HOST.toString),
      ConfOption.SPARK_MYSQL_USERNAME.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_USERNAME.toString),
      ConfOption.SPARK_MYSQL_PASSWORD.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_PASSWORD.toString),
      ConfOption.SPARK_MYSQL_PORT.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_PORT.toString, "3306"),
      ConfOption.SPARK_MYSQL_DB.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_DB.toString)
    )
    CollectionConf(collectionConf)
  }
}

trait CollectionConfConversions {
  implicit def scToCollectionConf(implicit sc: SparkContext): CollectionConf = CollectionConf.createCollectionConf(sc)
}
