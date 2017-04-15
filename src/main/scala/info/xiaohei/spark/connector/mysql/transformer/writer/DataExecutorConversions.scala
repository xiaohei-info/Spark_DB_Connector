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
    override protected var index: Int = 1

    override def execute(data: Int): PreparedStatement = {
      ps.setInt(index, data)
      ps
    }
  }

  implicit def longExecutor: DataExecutor[Long] = new DataExecutor[Long] {
    override protected var index: Int = 1

    override def execute(data: Long): PreparedStatement = {
      ps.setLong(index, data)
      ps
    }
  }

  implicit def shortExecutor: DataExecutor[Short] = new DataExecutor[Short] {
    override protected var index: Int = 1

    override def execute(data: Short): PreparedStatement = {
      ps.setShort(index, data)
      ps
    }
  }

  implicit def doubleExecutor: DataExecutor[Double] = new DataExecutor[Double] {
    override protected var index: Int = 1

    override def execute(data: Double): PreparedStatement = {
      ps.setDouble(index, data)
      ps
    }
  }

  implicit def floatExecutor: DataExecutor[Float] = new DataExecutor[Float] {
    override protected var index: Int = 1

    override def execute(data: Float): PreparedStatement = {
      ps.setFloat(index, data)
      ps
    }
  }

  implicit def booleanExecutor: DataExecutor[Boolean] = new DataExecutor[Boolean] {
    override protected var index: Int = 1

    override def execute(data: Boolean): PreparedStatement = {
      ps.setBoolean(index, data)
      ps
    }
  }

  implicit def bigDecimalExecutor: DataExecutor[java.math.BigDecimal] = new DataExecutor[java.math.BigDecimal] {
    override protected var index: Int = 1

    override def execute(data: java.math.BigDecimal): PreparedStatement = {
      ps.setBigDecimal(index, data)
      ps
    }
  }

  implicit def stringExecutor: DataExecutor[String] = new DataExecutor[String] {
    override protected var index: Int = 1

    override def execute(data: String): PreparedStatement = {
      ps.setString(index, data)
      ps
    }
  }
}
