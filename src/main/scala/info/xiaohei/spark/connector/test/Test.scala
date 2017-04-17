package info.xiaohei.spark.connector.test

import info.xiaohei.spark.connector.RelationalDbEntry
import info.xiaohei.spark.connector.mysql._
import info.xiaohei.spark.connector.mysql.transformer.executor.{CustomDataExecutor, DataExecutor}
import info.xiaohei.spark.connector.mysql.transformer.mapper.{CustomDataMapper, DataMapper}

/**
  * Author: xiaohei
  * Date: 2017/4/6
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */

case class Model(id: Int, name: String, age: Int)

object Test {
  def main(args: Array[String]) {

    implicit val mysqlConf = MysqlConf.createConf(
      "127.0.0.1",
      "root",
      "root",
      "3306",
      "its"
    )
    val entry = new RelationalDbEntry
    val res = entry.fromMysql[Model]("test")
      .select("id", "name", "age")
      .get
    res.foreach(x => println(s"id:${x.id},name:${x.name},age:${x.age}"))
  }

  implicit def myExecutorConversion: DataExecutor[Model] = new CustomDataExecutor[Model, (Int, String, Int)]() {
    override def convert(data: Model): (Int, String, Int) = (data.id, data.name, data.age)
  }

  implicit def myMapperConversion: DataMapper[Model] = new CustomDataMapper[(Int, String, Int), Model]() {
    override def convert(data: (Int, String, Int)): Model = Model(data._1, data._2, data._3)
  }
}
