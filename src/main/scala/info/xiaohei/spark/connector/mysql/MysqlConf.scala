package info.xiaohei.spark.connector.mysql

import org.apache.spark.SparkContext

/**
  * Author: xiaohei
  * Date: 2017/4/6
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */

private[mysql] object ConfOption extends Enumeration {
  type ConfOption = Value
  val SPARK_HBASE_HOST = Value("spark.hbase.host")
  val SPARK_MYSQL_HOST = Value("spark.mysql.host")
  val SPARK_MYSQL_USERNAME = Value("spark.mysql.username")
  val SPARK_MYSQL_PASSWORD = Value("spark.mysql.password")
  val SPARK_MYSQL_PORT = Value("spark.mysql.port")
  val SPARK_MYSQL_DB = Value("spark.mysql.db")
}

case class MysqlConf private[mysql](
                                     private val conf: collection.mutable.Map[String, String] = collection.mutable.Map.empty
                                   ) {
  def getMysqlInfo(): (String, String, String) = {
    require(conf.nonEmpty, "mysql conf must be set")
    val host = conf.get(ConfOption.SPARK_MYSQL_HOST.toString)
    val port = conf.get(ConfOption.SPARK_MYSQL_PORT.toString)
    val db = conf.get(ConfOption.SPARK_MYSQL_DB.toString)
    val username = conf.get(ConfOption.SPARK_MYSQL_USERNAME.toString)
    val password = conf.get(ConfOption.SPARK_MYSQL_PASSWORD.toString)
    val connectStr = s"jdbc:mysql://${host.get}:${port.get}/${db.get}"
    require(
      host.isDefined &&
        port.isDefined &&
        db.isDefined &&
        username.isDefined &&
        password.isDefined,
      "host/port/dbname/username/password must be set in mysql conf!"
    )
    (connectStr, username.get, password.get)
  }
}

object MysqlConf {
  def createConfFromSpark(sc: SparkContext) = {
    val sparkConf = sc.getConf
    val collectionConf = collection.mutable.Map[String, String](
      //ConfOption.SPARK_HBASE_HOST.toString -> sparkConf.get(ConfOption.SPARK_HBASE_HOST.toString),
      ConfOption.SPARK_MYSQL_HOST.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_HOST.toString),
      ConfOption.SPARK_MYSQL_USERNAME.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_USERNAME.toString),
      ConfOption.SPARK_MYSQL_PASSWORD.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_PASSWORD.toString),
      ConfOption.SPARK_MYSQL_PORT.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_PORT.toString, "3306"),
      ConfOption.SPARK_MYSQL_DB.toString -> sparkConf.get(ConfOption.SPARK_MYSQL_DB.toString)
    )
    MysqlConf(collectionConf)
  }

  def create() = {
    MysqlConf()
  }
}
