package info.xiaohei.spark.connector

import info.xiaohei.spark.connector.hbase.reader.{HBaseContextConversions, HBaseReaderBuilderConversions}
import info.xiaohei.spark.connector.hbase.writer.{CollectionWriterBuilderConversions, HBaseWriterBuilderConversions}
import info.xiaohei.spark.connector.transformer.reader.DataReaderConversions
import info.xiaohei.spark.connector.transformer.writer.DataWriterConversions


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

