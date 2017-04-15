package info.xiaohei.spark.connector.mysql.transformer.writer

import java.sql.PreparedStatement


/**
  * Author: xiaohei
  * Date: 2017/4/15
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait DataExecutorConversions extends Serializable {
  implicit def intExecutor: DataExecutor[Int] = new DataExecutor[Int] {
    override def prepare(ps: PreparedStatement, data: Int): Unit = {
      ps.setInt(index, data)
    }
  }

  implicit def longExecutor: DataExecutor[Long] = new DataExecutor[Long] {
    override def prepare(ps: PreparedStatement, data: Long): Unit = {
      ps.setLong(index, data)
    }
  }

  implicit def shortExecutor: DataExecutor[Short] = new DataExecutor[Short] {
    override def prepare(ps: PreparedStatement, data: Short): Unit = {
      ps.setShort(index, data)
    }
  }

  implicit def doubleExecutor: DataExecutor[Double] = new DataExecutor[Double] {
    override def prepare(ps: PreparedStatement, data: Double): Unit = {
      ps.setDouble(index, data)
    }
  }

  implicit def floatExecutor: DataExecutor[Float] = new DataExecutor[Float] {
    override def prepare(ps: PreparedStatement, data: Float): Unit = {
      ps.setFloat(index, data)
    }
  }

  implicit def booleanExecutor: DataExecutor[Boolean] = new DataExecutor[Boolean] {
    override def prepare(ps: PreparedStatement, data: Boolean): Unit = {
      ps.setBoolean(index, data)
    }
  }

  implicit def bigDecimalExecutor: DataExecutor[java.math.BigDecimal] = new DataExecutor[java.math.BigDecimal] {
    override def prepare(ps: PreparedStatement, data: java.math.BigDecimal): Unit = {
      ps.setBigDecimal(index, data)
    }
  }

  implicit def stringExecutor: DataExecutor[String] = new DataExecutor[String] {
    override def prepare(ps: PreparedStatement, data: String): Unit = {
      ps.setString(index, data)
    }
  }

  // todo:Options的空值处理

  //  implicit def optionWriter[T](implicit executor: DataExecutor[T]): DataExecutor[Option[T]] = new DataExecutor[Option[T]] {
  //    override def prepare(ps: PreparedStatement,data: Option[T]): Unit = {
  //      if (data.nonEmpty) {
  //        executor.prepare(data.get)
  //      } else {
  //        executor.prepare(null)
  //      }
  //    }
  //  }

  // Tuples

  //todo:index的设置方式
  implicit def tupleExecutor2[T1, T2](implicit e1: DataExecutor[T1], e2: DataExecutor[T2]): DataExecutor[(T1, T2)] = new DataExecutor[(T1, T2)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
    }
  }

  implicit def tupleExecutor3[T1, T2, T3](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3]): DataExecutor[(T1, T2, T3)] = new DataExecutor[(T1, T2, T3)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
    }
  }
}
