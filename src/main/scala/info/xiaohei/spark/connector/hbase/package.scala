package info.xiaohei.spark.connector

import info.xiaohei.spark.connector.hbase.reader.{FieldReaderConversions, HBaseContextConversions, HBaseReaderBuilderConversions}
import info.xiaohei.spark.connector.hbase.writer.{CollectionWriterBuilderConversions, DataWriterConversions, HBaseWriterBuilderConversions}


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
    with FieldReaderConversions
    with HBaseContextConversions

