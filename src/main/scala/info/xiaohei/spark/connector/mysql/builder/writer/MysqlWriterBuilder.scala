package info.xiaohei.spark.connector.mysql.builder.writer

import java.sql.{DriverManager, PreparedStatement}

import info.xiaohei.spark.connector.mysql.MysqlConf
import info.xiaohei.spark.connector.mysql.transformer.executor.DataExecutor

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
case class MysqlWriterBuilder[C] private[mysql](
                                                 private[mysql] val tableName: String,
                                                 private[mysql] val collectionData: Iterable[C],
                                                 //todo:t.productIterator.foreach{ i =>println("Value = " + i )}
                                                 private[mysql] val columns: Iterable[String] = Seq.empty,
                                                 //todo:完善
                                                 private[mysql] val whereConditions: Option[String] = None
                                               ) {
  def insert(cols: String*) = {
    require(this.columns.isEmpty, "Columns haven't been set")
    require(cols.nonEmpty, "Columns must by set,at least one")

    this.copy(columns = cols)
  }

  def where(conditions: String) = {
    this.copy(whereConditions = Some(conditions))
  }
}

private[mysql] class MysqlWriterBuildMaker[C](collectionData: Iterable[C])
  extends Serializable {
  def toMysql(tableName: String): MysqlWriterBuilder[C] =
    MysqlWriterBuilder[C](tableName, collectionData)
}

private[mysql] class MysqlWriter[C](builder: MysqlWriterBuilder[C])(implicit mysqlConf: MysqlConf, dataExecutor: DataExecutor[C])
  extends Serializable {
  def save(): Unit = {
    val (connectStr, username, password) = mysqlConf.getMysqlInfo()
    val conn = DriverManager.getConnection(connectStr, username, password)

    var placeholder = ""
    //todo:改进
    for (i <- 0 until builder.columns.size) placeholder += "?,"
    var sql = s"insert into ${builder.tableName}(${builder.columns.mkString(",")}) values(${placeholder.substring(0, placeholder.length - 1)})"
    if (builder.whereConditions.nonEmpty) {
      sql += s" where ${builder.whereConditions}"
    }
    val ps = conn.prepareStatement(sql)
    Class.forName("com.mysql.jdbc.Driver")
    try {
      builder.collectionData.foreach(x => dataExecutor.execute(ps, x))
    } catch {
      case e: Exception => e.printStackTrace()
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

trait MysqlWriterBuilderConversions extends Serializable {
  implicit def mysqlCollectionToBuildMaker[C](collectionData: Iterable[C])
  : MysqlWriterBuildMaker[C] = new MysqlWriterBuildMaker[C](collectionData)

  implicit def mysqlCollectionBuilderToWriter[C](builder: MysqlWriterBuilder[C])(implicit mysqlConf: MysqlConf, dataExecutor: DataExecutor[C])
  : MysqlWriter[C] = new MysqlWriter[C](builder)
}
