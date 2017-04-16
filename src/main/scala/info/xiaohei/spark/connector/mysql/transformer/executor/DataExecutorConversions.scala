package info.xiaohei.spark.connector.mysql.transformer.executor

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

  implicit def tupleExecutor4[T1, T2, T3, T4](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4]): DataExecutor[(T1, T2, T3, T4)] = new DataExecutor[(T1, T2, T3, T4)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
    }
  }

  implicit def tupleExecutor5[T1, T2, T3, T4, T5](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5]): DataExecutor[(T1, T2, T3, T4, T5)] = new DataExecutor[(T1, T2, T3, T4, T5)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
    }
  }

  implicit def tupleExecutor6[T1, T2, T3, T4, T5, T6](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6]): DataExecutor[(T1, T2, T3, T4, T5, T6)] = new DataExecutor[(T1, T2, T3, T4, T5, T6)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
    }
  }

  implicit def tupleExecutor7[T1, T2, T3, T4, T5, T6, T7](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
    }
  }

  implicit def tupleExecutor8[T1, T2, T3, T4, T5, T6, T7, T8](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
    }
  }

  implicit def tupleExecutor9[T1, T2, T3, T4, T5, T6, T7, T8, T9](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
    }
  }

  implicit def tupleExecutor10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
    }
  }

  implicit def tupleExecutor11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
    }
  }

  implicit def tupleExecutor12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
    }
  }

  implicit def tupleExecutor13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
    }
  }

  implicit def tupleExecutor14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13], e14: DataExecutor[T14]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
      e14.index = 14
      e14.prepare(ps, data._14)
    }
  }

  implicit def tupleExecutor15[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13], e14: DataExecutor[T14], e15: DataExecutor[T15]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
      e14.index = 14
      e14.prepare(ps, data._14)
      e15.index = 15
      e15.prepare(ps, data._15)
    }
  }

  implicit def tupleExecutor16[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13], e14: DataExecutor[T14], e15: DataExecutor[T15], e16: DataExecutor[T16]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
      e14.index = 14
      e14.prepare(ps, data._14)
      e15.index = 15
      e15.prepare(ps, data._15)
      e16.index = 16
      e16.prepare(ps, data._16)
    }
  }

  implicit def tupleExecutor17[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13], e14: DataExecutor[T14], e15: DataExecutor[T15], e16: DataExecutor[T16], e17: DataExecutor[T17]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
      e14.index = 14
      e14.prepare(ps, data._14)
      e15.index = 15
      e15.prepare(ps, data._15)
      e16.index = 16
      e16.prepare(ps, data._16)
      e17.index = 17
      e17.prepare(ps, data._17)
    }
  }

  implicit def tupleExecutor18[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13], e14: DataExecutor[T14], e15: DataExecutor[T15], e16: DataExecutor[T16], e17: DataExecutor[T17], e18: DataExecutor[T18]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
      e14.index = 14
      e14.prepare(ps, data._14)
      e15.index = 15
      e15.prepare(ps, data._15)
      e16.index = 16
      e16.prepare(ps, data._16)
      e17.index = 17
      e17.prepare(ps, data._17)
      e18.index = 18
      e18.prepare(ps, data._18)
    }
  }

  implicit def tupleExecutor19[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13], e14: DataExecutor[T14], e15: DataExecutor[T15], e16: DataExecutor[T16], e17: DataExecutor[T17], e18: DataExecutor[T18], e19: DataExecutor[T19]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
      e14.index = 14
      e14.prepare(ps, data._14)
      e15.index = 15
      e15.prepare(ps, data._15)
      e16.index = 16
      e16.prepare(ps, data._16)
      e17.index = 17
      e17.prepare(ps, data._17)
      e18.index = 18
      e18.prepare(ps, data._18)
      e19.index = 19
      e19.prepare(ps, data._19)
    }
  }

  implicit def tupleExecutor20[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13], e14: DataExecutor[T14], e15: DataExecutor[T15], e16: DataExecutor[T16], e17: DataExecutor[T17], e18: DataExecutor[T18], e19: DataExecutor[T19], e20: DataExecutor[T20]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
      e14.index = 14
      e14.prepare(ps, data._14)
      e15.index = 15
      e15.prepare(ps, data._15)
      e16.index = 16
      e16.prepare(ps, data._16)
      e17.index = 17
      e17.prepare(ps, data._17)
      e18.index = 18
      e18.prepare(ps, data._18)
      e19.index = 19
      e19.prepare(ps, data._19)
      e20.index = 20
      e20.prepare(ps, data._20)
    }
  }

  implicit def tupleExecutor21[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13], e14: DataExecutor[T14], e15: DataExecutor[T15], e16: DataExecutor[T16], e17: DataExecutor[T17], e18: DataExecutor[T18], e19: DataExecutor[T19], e20: DataExecutor[T20], e21: DataExecutor[T21]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
      e14.index = 14
      e14.prepare(ps, data._14)
      e15.index = 15
      e15.prepare(ps, data._15)
      e16.index = 16
      e16.prepare(ps, data._16)
      e17.index = 17
      e17.prepare(ps, data._17)
      e18.index = 18
      e18.prepare(ps, data._18)
      e19.index = 19
      e19.prepare(ps, data._19)
      e20.index = 20
      e20.prepare(ps, data._20)
      e21.index = 21
      e21.prepare(ps, data._21)
    }
  }

  implicit def tupleExecutor22[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22](implicit e1: DataExecutor[T1], e2: DataExecutor[T2], e3: DataExecutor[T3], e4: DataExecutor[T4], e5: DataExecutor[T5], e6: DataExecutor[T6], e7: DataExecutor[T7], e8: DataExecutor[T8], e9: DataExecutor[T9], e10: DataExecutor[T10], e11: DataExecutor[T11], e12: DataExecutor[T12], e13: DataExecutor[T13], e14: DataExecutor[T14], e15: DataExecutor[T15], e16: DataExecutor[T16], e17: DataExecutor[T17], e18: DataExecutor[T18], e19: DataExecutor[T19], e20: DataExecutor[T20], e21: DataExecutor[T21], e22: DataExecutor[T22]): DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] = new DataExecutor[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] {
    override def prepare(ps: PreparedStatement, data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)): Unit = {
      e1.index = 1
      e1.prepare(ps, data._1)
      e2.index = 2
      e2.prepare(ps, data._2)
      e3.index = 3
      e3.prepare(ps, data._3)
      e4.index = 4
      e4.prepare(ps, data._4)
      e5.index = 5
      e5.prepare(ps, data._5)
      e6.index = 6
      e6.prepare(ps, data._6)
      e7.index = 7
      e7.prepare(ps, data._7)
      e8.index = 8
      e8.prepare(ps, data._8)
      e9.index = 9
      e9.prepare(ps, data._9)
      e10.index = 10
      e10.prepare(ps, data._10)
      e11.index = 11
      e11.prepare(ps, data._11)
      e12.index = 12
      e12.prepare(ps, data._12)
      e13.index = 13
      e13.prepare(ps, data._13)
      e14.index = 14
      e14.prepare(ps, data._14)
      e15.index = 15
      e15.prepare(ps, data._15)
      e16.index = 16
      e16.prepare(ps, data._16)
      e17.index = 17
      e17.prepare(ps, data._17)
      e18.index = 18
      e18.prepare(ps, data._18)
      e19.index = 19
      e19.prepare(ps, data._19)
      e20.index = 20
      e20.prepare(ps, data._20)
      e21.index = 21
      e21.prepare(ps, data._21)
      e22.index = 22
      e22.prepare(ps, data._22)
    }
  }
}
