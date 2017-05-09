package info.xiaohei.spark.connector

import info.xiaohei.spark.connector.mysql.builder.reader.{MysqlContextConversions, MysqlReaderBuilderConversions}
import info.xiaohei.spark.connector.mysql.transformer.executor.DataExecutorConversions
import info.xiaohei.spark.connector.mysql.transformer.mapper.DataMapperConversions
import info.xiaohei.spark.connector.mysql.builder.writer.MysqlWriterBuilderConversions

/**
  * Author: xiaohei
  * Date: 2017/4/6
  * Email: xiaohei.info@gmail.com
  * Host: xiaohei.info
  */
package object mysql extends MysqlReaderBuilderConversions
  with MysqlWriterBuilderConversions
  with MysqlConfConversions
  with MysqlContextConversions
  with DataExecutorConversions
  with DataMapperConversions
