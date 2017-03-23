package info.xiaohei.spark.connector.hbase.writer

import info.xiaohei.spark.connector.hbase.HBaseConf
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.rdd.RDD

/**
  * Created by xiaohei on 2017/3/7.
  */
private[hbase] case class HBaseWriterBuilder[R](
                                                 private[hbase] val rdd: RDD[R],
                                                 private[hbase] val tableName: String,
                                                 //以下的参数通过方法动态设置
                                                 private[hbase] val defaultColumnFamily: Option[String] = None,
                                                 private[hbase] val columns: Iterable[String] = Seq.empty
                                               )
  extends Serializable {

  def selectColumns(cols: String*) = {
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

class HBaseWriterBuildMaker[R](rdd: RDD[R]) extends Serializable {
  def toHBaseTable(tableName: String) = HBaseWriterBuilder(rdd, tableName)
}

class HBaseWriter[R](builder: HBaseWriterBuilder[R])(implicit writer: FieldWriter[R]) extends Serializable {
  def save(): Unit = {
    val conf = HBaseConf.fromSpark(builder.rdd.context.getConf).createHadoopBaseConf()
    conf.set(TableOutputFormat.OUTPUT_TABLE, builder.tableName)

    val job = Job.getInstance(conf)
    job.setOutputFormatClass(classOf[TableOutputFormat[String]])

    val transRdd = builder.rdd.map {
      data =>
        val convertedData: Iterable[Option[Array[Byte]]] = writer.convertHBaseData(data)
        if (convertedData.size < 2) {
          throw new IllegalArgumentException("Expected at least two converted values, the first one should be the row key")
        }
        //val columnsNames = Utils.chosenColumns(builder.columns, writer.columns)
        require(builder.columns.nonEmpty, "No columns have been defined for the operation")
        val columnNames = builder.columns
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
        (new ImmutableBytesWritable, put)
    }

    transRdd.saveAsNewAPIHadoopDataset(job.getConfiguration)
  }
}

trait HBaseWriterBuilderConversions extends Serializable {

  implicit def rdd2HBaseBuildMaker[R](rdd: RDD[R]): HBaseWriterBuildMaker[R] = new HBaseWriterBuildMaker[R](rdd)

  implicit def builder2Writer[R](builder: HBaseWriterBuilder[R])(implicit writer: FieldWriter[R]): HBaseWriter[R] = new HBaseWriter[R](builder)
}





