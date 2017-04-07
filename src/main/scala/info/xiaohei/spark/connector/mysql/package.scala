package info.xiaohei.spark.connector

import info.xiaohei.spark.connector.mysql.reader.{MysqlConextConversions, MysqlReaderBuilderConversions}
import info.xiaohei.spark.connector.mysql.writer.MysqlWriterBuilderConversions

/**
  * Author: xiaohei
  * Date: 2017/4/6
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
package object mysql extends MysqlReaderBuilderConversions
  with MysqlWriterBuilderConversions
  with MysqlConfConversions
  with MysqlConextConversions
