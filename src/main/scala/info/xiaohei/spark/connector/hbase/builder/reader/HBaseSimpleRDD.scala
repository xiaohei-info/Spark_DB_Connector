package info.xiaohei.spark.connector.hbase.builder.reader

import info.xiaohei.spark.connector.hbase.HBaseCommonUtils
import info.xiaohei.spark.connector.hbase.transformer.reader.DataReader
import org.apache.hadoop.hbase.CellUtil
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.rdd.{NewHadoopRDD, RDD}
import org.apache.spark.{Partition, TaskContext}

import scala.reflect.ClassTag

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
//todo:SimpleHBaseRdd
class HBaseSimpleRDD[R: ClassTag](hadoopHBaseRDD: NewHadoopRDD[ImmutableBytesWritable, Result],
                                  builder: HBaseReaderBuilder[R], saltsLength: Int)
                                 (implicit reader: DataReader[R]) extends RDD[R](hadoopHBaseRDD) {
  @DeveloperApi
  override def compute(split: Partition, context: TaskContext): Iterator[R] = {
    firstParent[(ImmutableBytesWritable, Result)].iterator(split, context)
      .map(e => convert(e._1, e._2))
  }

  override protected def getPartitions: Array[Partition] = {
    firstParent[(ImmutableBytesWritable, Result)].partitions
  }

  private def convert(key: ImmutableBytesWritable, row: Result) = {
    //val columnNames = Utils.chosenColumns(builder.columns, reader.columns)
    require(builder.columns.nonEmpty, "No columns have been defined for the operation")
    val columnNames = builder.columns
    val columnsWithFamiy = HBaseCommonUtils.columnsWithFamily(builder.defaultColumnFamily, columnNames)
    val columns = columnsWithFamiy
      .map(t => (Bytes.toBytes(t._1), Bytes.toBytes(t._2)))
      .map {
        t =>
          if (row.containsColumn(t._1, t._2)) {
            Some(CellUtil.cloneValue(row.getColumnLatestCell(t._1, t._2)))
          } else {
            None
          }
      }.toList
    reader.read(Some(key.get.drop(saltsLength)) :: columns)
  }
}
