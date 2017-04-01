package info.xiaohei.spark.connector

import info.xiaohei.spark.connector.hbase.builder.reader.{HBaseContextConversions, HBaseReaderBuilderConversions}
import info.xiaohei.spark.connector.hbase.builder.writer.{CollectionWriterBuilderConversions, HBaseWriterBuilderConversions}
import info.xiaohei.spark.connector.hbase.transformer.reader.DataReaderConversions
import info.xiaohei.spark.connector.hbase.transformer.writer.DataWriterConversions


/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */

package object hbase
  extends HBaseWriterBuilderConversions
    with HBaseReaderBuilderConversions
    with CollectionWriterBuilderConversions
    with DataWriterConversions
    with DataReaderConversions
    with HBaseContextConversions

