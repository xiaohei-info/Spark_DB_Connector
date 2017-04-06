package info.xiaohei.spark.connector.mysql.reader

import java.sql.{DriverManager, ResultSet}

import info.xiaohei.spark.connector.mysql.MysqlConf

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
case class MysqlReaderBuilder(
                               private[mysql] val columns: Iterable[String] = Seq.empty,
                               private[mysql] val tableName: Option[String] = None,
                               private[mysql] val whereConditions: Option[String] = None
                             ) {

  def select(cols: String*) = {
    require(this.columns.isEmpty, "Columns haven't been set")
    require(cols.nonEmpty, "Columns must by set,at least one")

    this.copy(columns = cols)

  }

  def from(table: String) = {
    require(this.tableName.isEmpty, "Default table hasn't been set")
    require(table.nonEmpty, "Table must provided")

    this.copy(tableName = Some(table))
  }

  def where(conditions: String) = {
    this.copy(whereConditions = Some(conditions))
  }

}

trait MysqlReaderBuilderConversions extends Serializable {
  implicit def readFromMysql(builder: MysqlReaderBuilder)(implicit mysqlConf: MysqlConf): Option[ResultSet] = {
    val (connectStr, username, password) = mysqlConf.getMysqlInfo()
    val conn = DriverManager.getConnection(connectStr, username, password)
    var sql = s"select ${builder.columns.mkString(",")} from ${builder.tableName}"
    if (builder.whereConditions.nonEmpty) {
      sql += s" where ${builder.whereConditions}"
    }
    val ps = conn.prepareStatement(sql)
    Class.forName("com.mysql.jdbc.Driver")
    try {
      Some(ps.executeQuery())
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
