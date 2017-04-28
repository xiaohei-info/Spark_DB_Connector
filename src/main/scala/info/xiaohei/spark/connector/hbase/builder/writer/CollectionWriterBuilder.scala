package info.xiaohei.spark.connector.hbase.builder.writer

import info.xiaohei.spark.connector.hbase.HBaseConf
import info.xiaohei.spark.connector.hbase.salt.{SaltProducer, SaltProducerFactory}
import info.xiaohei.spark.connector.hbase.transformer.writer.DataWriter
import org.apache.hadoop.hbase.client.{HTable, Put}
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.JavaConversions._

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */

case class CollectionWriterBuilder[C] private[hbase](
                                                      private[hbase] val hBaseConf: HBaseConf,
                                                      private[hbase] val collectionData: Iterable[C],
                                                      private[hbase] val tableName: String,
                                                      private[hbase] val autoFlush: Option[(Boolean, Boolean)],
                                                      private[hbase] val writeBufferSize: Option[Long],
                                                      private[hbase] val defaultColumnFamily: Option[String] = None,
                                                      private[hbase] val columns: Iterable[String] = Seq.empty,
                                                      private[hbase] val salts: Iterable[String] = Seq.empty
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

  def withSalt(salts: Iterable[String]) = {
    require(salts.size > 1, "Invalid salting. Two or more elements are required")
    require(this.salts.isEmpty, "Salting has already been set")

    this.copy(salts = salts)
  }
}

private[hbase] class CollectionWriterBuildMaker[C](collectionData: Iterable[C])(implicit hBaseConf: HBaseConf) extends Serializable {
  def toHBase(tableName: String
              , autoFlush: Option[(Boolean, Boolean)] = None
              , writeBufferSize: Option[Long] = None)
  = CollectionWriterBuilder[C](hBaseConf, collectionData, tableName, autoFlush, writeBufferSize)
}

private[hbase] class CollectionWriter[C](builder: CollectionWriterBuilder[C])
                                        (implicit writer: DataWriter[C], saltProducerFactory: SaltProducerFactory[String]) extends Serializable {
  def save(): Unit = {
    //val conf = HBaseConf.createHBaseConf(builder.hbaseHost).createHadoopBaseConf()
    val conf = builder.hBaseConf.createHadoopBaseConf()

    val table = new HTable(conf, builder.tableName)
    //true为批量写,false为多线程并发写
    var batchOrMultiThread = true

    if (builder.autoFlush.nonEmpty && builder.writeBufferSize.nonEmpty) {
      val (autoFlush, clearBufferOnFail) = builder.autoFlush.get
      table.setAutoFlush(autoFlush, clearBufferOnFail)
      table.setWriteBufferSize(builder.writeBufferSize.get)
      batchOrMultiThread = false
    }

    val saltProducer: Option[SaltProducer[String]] = if (builder.salts.isEmpty) None else Some(saltProducerFactory.getHashProducer(builder.salts))

    def coverData(data: C): Put = {
      val convertedData: Iterable[Option[Array[Byte]]] = writer.write(data)
      if (convertedData.size < 2) {
        throw new IllegalArgumentException("Expected at least two converted values, the first one should be the row key")
      }
      //val columnsNames = Utils.chosenColumns(builder.columns, writer.columns)
      require(builder.columns.nonEmpty, "No columns have been defined for the operation")
      val columnNames = builder.columns
      val rawRowkey = convertedData.head.get
      val columnData = convertedData.drop(1)


      if (columnData.size != columnNames.size) {
        throw new IllegalArgumentException(s"Wrong number of columns. Expected ${columnNames.size} found ${columnData.size}")
      }
      val rowkey = if (saltProducer.isEmpty) rawRowkey else Bytes.toBytes(saltProducer.get.salting(rawRowkey) + Bytes.toString(rawRowkey))
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
  implicit def collectionToBuildMaker[C](collectionData: Iterable[C])(implicit hBaseConf: HBaseConf): CollectionWriterBuildMaker[C] = new CollectionWriterBuildMaker[C](collectionData)

  implicit def collectionBuilderToWriter[C](builder: CollectionWriterBuilder[C])(implicit writer: DataWriter[C], saltProducerFactory: SaltProducerFactory[String]): CollectionWriter[C] = new CollectionWriter[C](builder)
}
