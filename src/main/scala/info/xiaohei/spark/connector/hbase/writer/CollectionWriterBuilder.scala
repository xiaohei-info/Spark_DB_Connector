package info.xiaohei.spark.connector.hbase.writer

import info.xiaohei.spark.connector.hbase.HBaseConf
import org.apache.hadoop.hbase.client.{HTable, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.SparkContext

import scala.collection.JavaConversions._

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */

//todo:trait
private[hbase] case class CollectionWriterBuilder[C](
                                                      private[hbase] val sc: SparkContext,
                                                      private[hbase] val collectionData: Iterable[C],
                                                      private[hbase] val tableName: String,
                                                      private[hbase] val autoFlush: Option[(Boolean, Boolean)],
                                                      private[hbase] val writeBufferSize: Option[Long],
                                                      private[hbase] val defaultColumnFamily: Option[String] = None,
                                                      private[hbase] val columns: Iterable[String] = Seq.empty
                                                    ) {
  def insert(cols: String*) = {
    require(this.columns.isEmpty, "Columns haven't been set")
    require(cols.nonEmpty, "Columns must by set,at least one")
    this.copy(columns = cols)
  }

  def inColumnFamily(family: String) = {
    require(this.defaultColumnFamily.isEmpty, "Default column family hasn't been set")
    require(family.nonEmpty, "Column family must provided")
    this.copy(defaultColumnFamily = Some(family))
  }
}

//todo:trait
//todo:collectionData implicit
class CollectionWriterBuildMaker[C](collectionData: Iterable[C]) {
  def toHBase(sc: SparkContext
              , tableName: String
              , autoFlush: Option[(Boolean, Boolean)] = None
              , writeBufferSize: Option[Long] = None)
  = CollectionWriterBuilder[C](sc, collectionData, tableName, autoFlush, writeBufferSize)
}

//todo:trait
class CollectionWriter[C](builder: CollectionWriterBuilder[C])(implicit writer: DataWriter[C]) extends Serializable {
  def save(): Unit = {
    val conf = HBaseConf.fromSpark(builder.sc.getConf).createHadoopBaseConf()

    val table = new HTable(conf, builder.tableName)
    //true为批量写,false为多线程并发写
    var batchOrMultiThread = true
    //todo:方法
    if (builder.autoFlush.nonEmpty) {
      val (autoFlush, clearBufferOnFail) = builder.autoFlush.get
      table.setAutoFlush(autoFlush, clearBufferOnFail)
      batchOrMultiThread = false
    }
    if (builder.writeBufferSize.nonEmpty) {
      table.setWriteBufferSize(builder.writeBufferSize.get)
      batchOrMultiThread = false
    }

    def coverData(data: C): Put = {
      val convertedData: Iterable[Option[Array[Byte]]] = writer.convertHBaseData(data)
      if (convertedData.size < 2) {
        throw new IllegalArgumentException("Expected at least two converted values, the first one should be the row key")
      }
      //val columnsNames = Utils.chosenColumns(builder.columns, writer.columns)
      require(builder.columns.nonEmpty, "No columns have been defined for the operation")
      val columnNames= builder.columns
      val rowkey = convertedData.head.get
      val columnData = convertedData.drop(1)


      if (columnData.size != columnNames.size) {
        throw new IllegalArgumentException(s"Wrong number of columns. Expected ${columnNames.size} found ${columnData.size}")
      }

      val put = new Put(rowkey)
      columnNames.zip(columnData).foreach {
        case (name, Some(value)) =>
          val family = if (name.contains(":")) Bytes.toBytes(name.substring(0, name.indexOf(":"))) else Bytes.toBytes(builder.defaultColumnFamily.get)
          val column = if (name.contains(":")) Bytes.toBytes(name.substring(name.indexOf(":") + 1)) else Bytes.toBytes(name)
          put.add(family, column, value)
        case _ =>
      }
      put
    }

    if (batchOrMultiThread) {
      val putList = builder.collectionData.map(coverData).toList
      table.put(putList)
    } else {
      builder.collectionData.foreach(data => table.put(coverData(data)))
      table.flushCommits()
    }
  }

}


trait CollectionWriterBuilderConversions extends Serializable {
  implicit def collectionToBuildMaker[C](collectionData: Iterable[C]): CollectionWriterBuildMaker[C] = new CollectionWriterBuildMaker[C](collectionData)

  implicit def builderToWriter[C](builder: CollectionWriterBuilder[C])(implicit writer: DataWriter[C]): CollectionWriter[C] = new CollectionWriter[C](builder)
}