package info.xiaohei.spark.connector.hbase

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: yuande.jiang@fugetech.com
  * Host: www.xiaohei.info
  */
private[hbase] object HBaseCommonUtils {

  def columnsWithFamily(defaultColumnFamily: Option[String], columns: Iterable[String]): Iterable[(String, String)] = {
    columns.map {
      c =>
        if (c.contains(":")) {
          (c.substring(0, c.indexOf(":")), c.substring(c.indexOf(":") + 1))
        }
        else if (defaultColumnFamily.isEmpty) {
          throw new IllegalArgumentException("Default column family is mandatory when column names are not fully qualified")
        }
        else {
          (defaultColumnFamily.get, c)
        }
    }
  }

  def getFullColumnNames(defaultColumnFamily: Option[String], columns: Iterable[String]): Iterable[String] = {
    columnsWithFamily(defaultColumnFamily, columns).map {
      case (f, c) => s"$f:$c"
    }

  }
}
