package info.xiaohei.spark.connector.hbase

/**
  * Created by xiaohei on 2017/3/20.
  */
object Utils {
//  def chosenColumns(colOptions: Iterable[String]*): Iterable[String] = {
//    val valid = colOptions.filter(c => c.nonEmpty)
//    if (valid.isEmpty) throw new IllegalArgumentException("No columns have been defined for the operation")
//    if (valid.size > 1) throw new IllegalArgumentException("Columns are defined twice, you must define them only once")
//    valid.head
//  }

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
