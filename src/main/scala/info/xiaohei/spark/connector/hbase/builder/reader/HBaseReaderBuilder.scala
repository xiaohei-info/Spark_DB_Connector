package info.xiaohei.spark.connector.hbase.builder.reader

import info.xiaohei.spark.connector.hbase.{HBaseCommonUtils, HBaseConf}
import info.xiaohei.spark.connector.hbase.transformer.reader.DataReader
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.SparkContext
import org.apache.spark.rdd.{NewHadoopRDD, RDD}

import scala.reflect.ClassTag

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
case class HBaseReaderBuilder[R: ClassTag] private[hbase](
                                                           @transient sc: SparkContext,
                                                           private[hbase] val tableName: String,
                                                           private[hbase] val defaultColumnFamily: Option[String] = None,
                                                           private[hbase] val columns: Iterable[String] = Seq.empty,
                                                           private[hbase] val startRow: Option[String] = None,
                                                           private[hbase] val stopRow: Option[String] = None
                                                         ) {
  def select(columns: String*): HBaseReaderBuilder[R] = {
    require(this.columns.isEmpty, "Columns have already been set")
    require(columns.nonEmpty, "You should provide at least one column")
    this.copy(columns = columns)
  }

  def inColumnFamily(columnFamily: String): HBaseReaderBuilder[R] = {
    require(this.defaultColumnFamily.isEmpty, "Default column family has already been set")
    require(columnFamily.nonEmpty, "Invalid column family provided")
    this.copy(defaultColumnFamily = Some(columnFamily))
  }

  def withStartRow(startRow: String): HBaseReaderBuilder[R] = {
    require(startRow.nonEmpty, s"Invalid start row '$startRow'")
    require(this.startRow.isEmpty, "Start row has already been set")
    this.copy(startRow = Some(startRow))
  }

  def withEndRow(endRow: String): HBaseReaderBuilder[R] = {
    require(endRow.nonEmpty, s"Invalid stop row '$endRow'")
    require(this.stopRow.isEmpty, "Stop row has already been set")
    this.copy(stopRow = Some(endRow))
  }
}

trait HBaseReaderBuilderConversions extends Serializable {
  implicit def toHBaseRDD[R: ClassTag](builder: HBaseReaderBuilder[R])(implicit reader: DataReader[R]): RDD[R] = {
    val hbaseConfig = HBaseConf.createFromSpark(builder.sc.getConf).createHadoopBaseConf()
    hbaseConfig.set(TableInputFormat.INPUT_TABLE, builder.tableName)
    require(builder.columns.nonEmpty, "No columns have been defined for the operation")
    val columnNames = builder.columns
    val fullColumnNames = HBaseCommonUtils.getFullColumnNames(builder.defaultColumnFamily, columnNames)
    if (fullColumnNames.nonEmpty) {
      hbaseConfig.set(TableInputFormat.SCAN_COLUMNS, fullColumnNames.mkString(" "))
    }
    if (builder.startRow.nonEmpty) {
      hbaseConfig.set(TableInputFormat.SCAN_ROW_START, builder.startRow.get)
    }
    if (builder.stopRow.nonEmpty) {
      hbaseConfig.set(TableInputFormat.SCAN_ROW_STOP, builder.stopRow.get)
    }
    //todo:asInstanceOf
    val rdd = builder.sc.newAPIHadoopRDD(hbaseConfig
      , classOf[TableInputFormat]
      , classOf[ImmutableBytesWritable]
      , classOf[Result])
      .asInstanceOf[NewHadoopRDD[ImmutableBytesWritable, Result]]

    new SimpleHBaseRdd[R](rdd, builder)
  }
}
