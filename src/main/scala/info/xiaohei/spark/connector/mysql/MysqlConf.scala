package info.xiaohei.spark.connector.mysql

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

case class MysqlConf(
                      private val conf: collection.mutable.Map[String, String] = collection.mutable.Map.empty
                    ) {


  def set(key: String, value: String): Unit = {
    conf += key -> value
  }

  def get(key: String): Option[String] = {
    conf.get(key)
  }
}

object MysqlConf {
  private[mysql] def createConfFromSpark(sc: SparkContext) = {
    val sparkConf = sc.getConf
    val collectionConf = collection.mutable.Map[String, String](
      ConfOption.SPARK_HBASE_HOST.toString -> sparkConf.get(ConfOption.SPARK_HBASE_HOST.toString),
      ConfOption.SPARK_MYSQL_HOST.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_HOST.toString),
      ConfOption.SPARK_MYSQL_USERNAME.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_USERNAME.toString),
      ConfOption.SPARK_MYSQL_PASSWORD.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_PASSWORD.toString),
      ConfOption.SPARK_MYSQL_PORT.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_PORT.toString, "3306"),
      ConfOption.SPARK_MYSQL_DB.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_DB.toString)
    )
    MysqlConf(collectionConf)
  }

  def createConf() = {
    MysqlConf()
  }
}

trait MysqlConfConversions {
  implicit def scToCollectionConf(implicit sc: SparkContext): MysqlConf = MysqlConf.createConfFromSpark(sc)
}
