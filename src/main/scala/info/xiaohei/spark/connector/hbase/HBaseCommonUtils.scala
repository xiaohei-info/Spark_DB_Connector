package info.xiaohei.spark.connector.hbase

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
private[hbase] object HBaseCommonUtils {

  def chooseColums(colOpts: Iterable[String]*): Iterable[String] = {
    val cols = colOpts.filter(_.nonEmpty)
    if (cols.isEmpty) throw new IllegalArgumentException("No columns have been set for current operation")
    if (cols.size > 1) throw new IllegalArgumentException("Columns are defind twice,it must only define once")
    cols.head
  }

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
