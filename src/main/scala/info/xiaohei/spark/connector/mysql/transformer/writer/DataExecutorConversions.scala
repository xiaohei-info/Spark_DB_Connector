package info.xiaohei.spark.connector.mysql.transformer.writer

import com.mysql.jdbc.PreparedStatement

/**
  * Author: xiaohei
  * Date: 2017/4/15
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait DataExecutorConversions extends Serializable {
  implicit def intExecutor: DataExecutor[Int] = new DataExecutor[Int] {
    override def prepare(data: Int): PreparedStatement = {
      ps.setInt(index, data)
      ps
    }
  }

  implicit def longExecutor: DataExecutor[Long] = new DataExecutor[Long] {
    override def prepare(data: Long): PreparedStatement = {
      ps.setLong(index, data)
      ps
    }
  }

  implicit def shortExecutor: DataExecutor[Short] = new DataExecutor[Short] {
    override def prepare(data: Short): PreparedStatement = {
      ps.setShort(index, data)
      ps
    }
  }

  implicit def doubleExecutor: DataExecutor[Double] = new DataExecutor[Double] {
    override def prepare(data: Double): PreparedStatement = {
      ps.setDouble(index, data)
      ps
    }
  }

  implicit def floatExecutor: DataExecutor[Float] = new DataExecutor[Float] {
    override def prepare(data: Float): PreparedStatement = {
      ps.setFloat(index, data)
      ps
    }
  }

  implicit def booleanExecutor: DataExecutor[Boolean] = new DataExecutor[Boolean] {
    override def prepare(data: Boolean): PreparedStatement = {
      ps.setBoolean(index, data)
      ps
    }
  }

  implicit def bigDecimalExecutor: DataExecutor[java.math.BigDecimal] = new DataExecutor[java.math.BigDecimal] {
    override def prepare(data: java.math.BigDecimal): PreparedStatement = {
      ps.setBigDecimal(index, data)
      ps
    }
  }

  implicit def stringExecutor: DataExecutor[String] = new DataExecutor[String] {
    override def prepare(data: String): PreparedStatement = {
      ps.setString(index, data)
      ps
    }
  }

  // todo:Options的空值处理

  //  implicit def optionWriter[T](implicit executor: DataExecutor[T]): DataExecutor[Option[T]] = new DataExecutor[Option[T]] {
  //    override def prepare(data: Option[T]): PreparedStatement = {
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
    override def prepare(data: (T1, T2)): PreparedStatement = {
      e1.index = 1
      e1.prepare(data._1)
      e2.index = 2
      e2.prepare(data._2)
    }
  }

  implicit def tupleExecutor3[T1, T2, T3](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3]): DataExecutor[(T1, T2, T3)] = new DataExecutor[(T1, T2, T3)] {
    override def prepare(data: (T1, T2, T3)): PreparedStatement = {
      e1.index = 1
      e1.prepare(data._1)
      e2.index = 2
      e2.prepare(data._2)
      e3.index = 3
      e3.prepare(data._3)
    }
  }
}
