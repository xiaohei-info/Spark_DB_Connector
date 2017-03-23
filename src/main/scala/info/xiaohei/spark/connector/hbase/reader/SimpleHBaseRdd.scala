package info.xiaohei.spark.connector.hbase.reader

import info.xiaohei.spark.connector.hbase.Utils
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
  * Email yuande.jiang@fugetech.com
  * Last Modified by: xiaohei
  * Last Modified time: 2017/3/21
  */
//todo:SimpleHBaseRdd
class SimpleHBaseRdd[R: ClassTag](hadoopHBaseRDD: NewHadoopRDD[ImmutableBytesWritable, Result],
                                  builder: HBaseReaderBuilder[R])
                                 (implicit reader: FieldReader[R]) extends RDD[R](hadoopHBaseRDD) {
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
    val columnsWithFamiy = Utils.columnsWithFamily(builder.defaultColumnFamily, columnNames)
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
    reader.revertFromHBaseData(Some(key.get) :: columns)
  }
}
