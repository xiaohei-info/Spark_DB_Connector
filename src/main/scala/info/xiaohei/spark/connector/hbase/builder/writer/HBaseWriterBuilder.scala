package info.xiaohei.spark.connector.hbase.builder.writer

import info.xiaohei.spark.connector.hbase.HBaseConf
import info.xiaohei.spark.connector.hbase.salt.{SaltProducer, SaltProducerFactory}
import info.xiaohei.spark.connector.hbase.transformer.writer.DataWriter
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.rdd.RDD

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
case class HBaseWriterBuilder[R] private[hbase](
                                                 private[hbase] val rdd: RDD[R],
                                                 private[hbase] val tableName: String,
                                                 //以下的参数通过方法动态设置
                                                 private[hbase] val defaultColumnFamily: Option[String] = None,
                                                 private[hbase] val columns: Iterable[String] = Seq.empty,
                                                 private[hbase] val salts: Iterable[String] = Seq.empty
                                               )
  extends Serializable {

  def insert(cols: String*) = {
    require(this.columns.isEmpty, "Columns haven't been set")
    require(cols.nonEmpty, "Columns must by set,at least one")
    this.copy(columns = cols)
  }

  def insert(cols: Iterable[String]) = {
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

private[hbase] class HBaseWriterBuildMaker[R](rdd: RDD[R]) extends Serializable {
  def toHBase(tableName: String) = HBaseWriterBuilder(rdd, tableName)
}

private[hbase] class HBaseWriter[R](builder: HBaseWriterBuilder[R])(implicit writer: DataWriter[R]
                                                                    , saltProducerFactory: SaltProducerFactory[String]) extends Serializable {
  def save(): Unit = {
    val conf = HBaseConf.createFromSpark(builder.rdd.context.getConf).createHadoopBaseConf()
    conf.set(TableOutputFormat.OUTPUT_TABLE, builder.tableName)

    val job = Job.getInstance(conf)
    job.setOutputFormatClass(classOf[TableOutputFormat[String]])

    val saltProducer: Option[SaltProducer[String]] = if (builder.salts.isEmpty) None else Some(saltProducerFactory.getHashProducer(builder.salts))

    val transRdd = builder.rdd.map {
      data =>
        val convertedData: Iterable[Option[Array[Byte]]] = writer.write(data)
        if (convertedData.size < 2) {
          throw new IllegalArgumentException("Expected at least two converted values, the first one should be the row key")
        }
        require(builder.columns.nonEmpty, "No columns have been defined for the operation")
        val columnNames = builder.columns
        val rawRowkey = convertedData.head.get
        val columnData = convertedData.drop(1)

        if (columnData.size != columnNames.size) {
          throw new IllegalArgumentException(s"Wrong number of columns. Expected ${columnNames.size} found ${columnData.size}")
        }
        //transform rowkey with salt
        val rowkey = if (saltProducer.isEmpty) rawRowkey else Bytes.toBytes(saltProducer.get.salting(rawRowkey) + Bytes.toString(rawRowkey))
        val put = new Put(rowkey)
        columnNames.zip(columnData).foreach {
          case (name, Some(value)) =>
            val family = if (name.contains(":")) Bytes.toBytes(name.substring(0, name.indexOf(":"))) else Bytes.toBytes(builder.defaultColumnFamily.get)
            val column = if (name.contains(":")) Bytes.toBytes(name.substring(name.indexOf(":") + 1)) else Bytes.toBytes(name)
            put.addColumn(family, column, value)
          case _ =>
        }
        (new ImmutableBytesWritable, put)
    }
    transRdd.saveAsNewAPIHadoopDataset(job.getConfiguration)
  }
}

trait HBaseWriterBuilderConversions extends Serializable {

  implicit def rddToHBaseBuildMaker[R](rdd: RDD[R]): HBaseWriterBuildMaker[R] = new HBaseWriterBuildMaker[R](rdd)

  implicit def builderToWriter[R](builder: HBaseWriterBuilder[R])(implicit writer: DataWriter[R], saltProducerFactory: SaltProducerFactory[String]): HBaseWriter[R] = new HBaseWriter[R](builder)
}





