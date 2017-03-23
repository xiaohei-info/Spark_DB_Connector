package info.xiaohei.spark.connector

import info.xiaohei.spark.connector.hbase.reader.{FieldReaderConversions, HBaseContextConversions, HBaseReaderBuilderConversions}
import info.xiaohei.spark.connector.hbase.writer.{CollectionWriterBuilderConversions, FieldWriterConversions, HBaseWriterBuilderConversions}


/**
  * Created by xiaohei on 2017/3/8.
  */

package object hbase
  extends HBaseWriterBuilderConversions
    with HBaseReaderBuilderConversions
    with CollectionWriterBuilderConversions
    with FieldWriterConversions
    with FieldReaderConversions
    with HBaseContextConversions

