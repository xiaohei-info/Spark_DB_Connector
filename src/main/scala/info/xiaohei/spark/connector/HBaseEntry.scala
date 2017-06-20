package info.xiaohei.spark.connector

import info.xiaohei.spark.connector.hbase.salt.SaltProducerFactory
import info.xiaohei.spark.connector.hbase.transformer.writer.SingleColumnDataWriter
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Get}
import org.apache.hadoop.hbase.util.Bytes

/**
  * Author: xiaohei
  * Date: 2017/6/10
  * Email: xiaohei.info@gmail.com
  * Host: xiaohei.info
  */
class HBaseEntry extends Serializable {
  def singleQuery(tableName: String, rowkey: String, salts: Iterable[String],
                  columnFamily: String, columns: Iterable[String]) = {
    val finalRowkey = if (salts.isEmpty) {
      rowkey
    } else {
      val saltProducer = new SaltProducerFactory[String]().getHashProducer(salts)
      val writer = new SingleColumnDataWriter[String] {
        override def writeSingleColumn(data: String): Option[Array[Byte]] = Some(Bytes.toBytes(data))
      }
      val rawRowkey = writer.writeSingleColumn(rowkey).get
      saltProducer.salting(rawRowkey) + Bytes.toString(rawRowkey)
    }

    val conf = HBaseConfiguration.create()
    val connection = ConnectionFactory.createConnection(conf)
    val table = connection.getTable(TableName.valueOf(tableName))
    val get = new Get(Bytes.toBytes(finalRowkey))
    for (col <- columns) {
      get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(col))
    }
    val result = table.get(get)

    val res = new collection.mutable.ListBuffer[String]()
    val cells = result.listCells().iterator()
    while (cells.hasNext) {
      res.append(Bytes.toString(CellUtil.cloneValue(cells.next())))
    }
    res
  }


}
