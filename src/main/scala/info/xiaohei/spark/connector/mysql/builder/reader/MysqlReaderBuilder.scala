package info.xiaohei.spark.connector.mysql.builder.reader

import java.sql.DriverManager

import info.xiaohei.spark.connector.mysql.MysqlConf
import info.xiaohei.spark.connector.mysql.transformer.mapper.DataMapper

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: xiaohei.info@gmail.com
  * Host: xiaohei.info
  */
case class MysqlReaderBuilder[T](
                                  private[mysql] val tableName: String,
                                  private[mysql] val columns: Iterable[String] = Seq.empty,
                                  private[mysql] val whereConditions: Option[String] = None
                                ) {

  def select(cols: String*): MysqlReaderBuilder[T] = {
    require(this.columns.isEmpty, "Columns haven't been set")
    require(cols.nonEmpty, "Columns must by set,at least one")

    this.copy(columns = cols)
  }

  def select(cols: Iterable[String]): MysqlReaderBuilder[T] = {
    require(this.columns.isEmpty, "Columns haven't been set")
    require(cols.nonEmpty, "Columns must by set,at least one")

    this.copy(columns = cols)
  }

  def where(conditions: String): MysqlReaderBuilder[T] = {
    this.copy(whereConditions = Some(conditions))
  }

}

trait MysqlReaderBuilderConversions extends Serializable {
  implicit def readFromMysql[T](builder: MysqlReaderBuilder[T])
                               (implicit mysqlConf: MysqlConf, dataMapper: DataMapper[T]): Option[Seq[T]] = {
    require(builder.columns.nonEmpty, "column names must be set!")

    val (connectStr, username, password) = mysqlConf.getMysqlInfo()
    val conn = DriverManager.getConnection(connectStr, username, password)
    var sql = s"select ${builder.columns.mkString(",")} from ${builder.tableName}"
    if (builder.whereConditions.nonEmpty) {
      sql += s" where ${builder.whereConditions}"
    }
    val ps = conn.prepareStatement(sql)
    Class.forName("com.mysql.jdbc.Driver")
    try {
      val resultList = new collection.mutable.ListBuffer[T]
      val resultSet = ps.executeQuery()
      while (resultSet.next()) {
        resultList += dataMapper.map(resultSet)
      }
      Some(resultList)
    }
    catch {
      case e: Exception => e.printStackTrace()
        None
    } finally {
      if (ps != null) {
        ps.close()
      }
      if (conn != null) {
        conn.close()
      }
    }
  }
}
