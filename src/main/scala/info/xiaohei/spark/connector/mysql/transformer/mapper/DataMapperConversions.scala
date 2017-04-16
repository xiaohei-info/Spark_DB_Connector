package info.xiaohei.spark.connector.mysql.transformer.mapper

import java.math.BigDecimal
import java.sql.ResultSet

/**
  * Author: xiaohei
  * Date: 2017/4/16
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait DataMapperConversions extends Serializable {
  implicit def intMapper: DataMapper[Int] = new DataMapper[Int] {
    override def map(resultSet: ResultSet): Int = {
      resultSet.getInt(index)
    }
  }

  implicit def longMapper: DataMapper[Long] = new DataMapper[Long] {
    override def map(resultSet: ResultSet): Long = {
      resultSet.getLong(index)
    }
  }

  implicit def shortMapper: DataMapper[Short] = new DataMapper[Short] {
    override def map(resultSet: ResultSet): Short = {
      resultSet.getShort(index)
    }
  }

  implicit def doubleMapper: DataMapper[Double] = new DataMapper[Double] {
    override def map(resultSet: ResultSet): Double = {
      resultSet.getDouble(index)
    }
  }

  implicit def floatMapper: DataMapper[Float] = new DataMapper[Float] {
    override def map(resultSet: ResultSet): Float = {
      resultSet.getFloat(index)
    }
  }

  implicit def booleanMapper: DataMapper[Boolean] = new DataMapper[Boolean] {
    override def map(resultSet: ResultSet): Boolean = {
      resultSet.getBoolean(index)
    }
  }

  implicit def bigDecimalMapper: DataMapper[java.math.BigDecimal] = new DataMapper[java.math.BigDecimal] {
    override def map(resultSet: ResultSet): BigDecimal = {
      resultSet.getBigDecimal(index)
    }
  }

  implicit def stringMapper: DataMapper[String] = new DataMapper[String] {
    override def map(resultSet: ResultSet): String = {
      resultSet.getString(index)
    }
  }

  implicit def tupleMapper2[T1, T2](implicit m1: DataMapper[T1], m2: DataMapper[T2]): DataMapper[(T1, T2)] = new DataMapper[(T1, T2)] {
    override def map(resultSet: ResultSet): (T1, T2) = {
      m1.index = 1
      m2.index = 2
      (m1.map(resultSet), m2.map(resultSet))
    }
  }

  implicit def tupleMapper3[T1, T2, T3](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3]): DataMapper[(T1, T2, T3)] = new DataMapper[(T1, T2, T3)] {
    override def map(resultSet: ResultSet): (T1, T2, T3) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet))
    }
  }


  implicit def tupleMapper4[T1, T2, T3, T4](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4]): DataMapper[(T1, T2, T3, T4)] = new DataMapper[(T1, T2, T3, T4)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet))
    }
  }

  implicit def tupleMapper5[T1, T2, T3, T4, T5](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5]): DataMapper[(T1, T2, T3, T4, T5)] = new DataMapper[(T1, T2, T3, T4, T5)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet))
    }
  }

  implicit def tupleMapper6[T1, T2, T3, T4, T5, T6](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6]): DataMapper[(T1, T2, T3, T4, T5, T6)] = new DataMapper[(T1, T2, T3, T4, T5, T6)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet))
    }
  }

  implicit def tupleMapper7[T1, T2, T3, T4, T5, T6, T7](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7]): DataMapper[(T1, T2, T3, T4, T5, T6, T7)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet))
    }
  }

  implicit def tupleMapper8[T1, T2, T3, T4, T5, T6, T7, T8](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet))
    }
  }

  implicit def tupleMapper9[T1, T2, T3, T4, T5, T6, T7, T8, T9](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet))
    }
  }

  implicit def tupleMapper10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet))
    }
  }

  implicit def tupleMapper11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet))
    }
  }

  implicit def tupleMapper12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet))
    }
  }

  implicit def tupleMapper13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet))
    }
  }

  implicit def tupleMapper14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13], m14: DataMapper[T14]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      m14.index = 14
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet), m14.map(resultSet))
    }
  }

  implicit def tupleMapper15[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13], m14: DataMapper[T14], m15: DataMapper[T15]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      m14.index = 14
      m15.index = 15
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet), m14.map(resultSet), m15.map(resultSet))
    }
  }

  implicit def tupleMapper16[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13], m14: DataMapper[T14], m15: DataMapper[T15], m16: DataMapper[T16]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      m14.index = 14
      m15.index = 15
      m16.index = 16
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet), m14.map(resultSet), m15.map(resultSet), m16.map(resultSet))
    }
  }

  implicit def tupleMapper17[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13], m14: DataMapper[T14], m15: DataMapper[T15], m16: DataMapper[T16], m17: DataMapper[T17]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      m14.index = 14
      m15.index = 15
      m16.index = 16
      m17.index = 17
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet), m14.map(resultSet), m15.map(resultSet), m16.map(resultSet), m17.map(resultSet))
    }
  }

  implicit def tupleMapper18[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13], m14: DataMapper[T14], m15: DataMapper[T15], m16: DataMapper[T16], m17: DataMapper[T17], m18: DataMapper[T18]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      m14.index = 14
      m15.index = 15
      m16.index = 16
      m17.index = 17
      m18.index = 18
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet), m14.map(resultSet), m15.map(resultSet), m16.map(resultSet), m17.map(resultSet), m18.map(resultSet))
    }
  }

  implicit def tupleMapper19[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13], m14: DataMapper[T14], m15: DataMapper[T15], m16: DataMapper[T16], m17: DataMapper[T17], m18: DataMapper[T18], m19: DataMapper[T19]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      m14.index = 14
      m15.index = 15
      m16.index = 16
      m17.index = 17
      m18.index = 18
      m19.index = 19
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet), m14.map(resultSet), m15.map(resultSet), m16.map(resultSet), m17.map(resultSet), m18.map(resultSet), m19.map(resultSet))
    }
  }

  implicit def tupleMapper20[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13], m14: DataMapper[T14], m15: DataMapper[T15], m16: DataMapper[T16], m17: DataMapper[T17], m18: DataMapper[T18], m19: DataMapper[T19], m20: DataMapper[T20]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      m14.index = 14
      m15.index = 15
      m16.index = 16
      m17.index = 17
      m18.index = 18
      m19.index = 19
      m20.index = 20
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet), m14.map(resultSet), m15.map(resultSet), m16.map(resultSet), m17.map(resultSet), m18.map(resultSet), m19.map(resultSet), m20.map(resultSet))
    }
  }

  implicit def tupleMapper21[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13], m14: DataMapper[T14], m15: DataMapper[T15], m16: DataMapper[T16], m17: DataMapper[T17], m18: DataMapper[T18], m19: DataMapper[T19], m20: DataMapper[T20], m21: DataMapper[T21]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      m14.index = 14
      m15.index = 15
      m16.index = 16
      m17.index = 17
      m18.index = 18
      m19.index = 19
      m20.index = 20
      m21.index = 21
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet), m14.map(resultSet), m15.map(resultSet), m16.map(resultSet), m17.map(resultSet), m18.map(resultSet), m19.map(resultSet), m20.map(resultSet), m21.map(resultSet))
    }
  }

  implicit def tupleMapper22[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22](implicit m1: DataMapper[T1], m2: DataMapper[T2], m3: DataMapper[T3], m4: DataMapper[T4], m5: DataMapper[T5], m6: DataMapper[T6], m7: DataMapper[T7], m8: DataMapper[T8], m9: DataMapper[T9], m10: DataMapper[T10], m11: DataMapper[T11], m12: DataMapper[T12], m13: DataMapper[T13], m14: DataMapper[T14], m15: DataMapper[T15], m16: DataMapper[T16], m17: DataMapper[T17], m18: DataMapper[T18], m19: DataMapper[T19], m20: DataMapper[T20], m21: DataMapper[T21], m22: DataMapper[T22]): DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] = new DataMapper[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] {
    override def map(resultSet: ResultSet): (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22) = {
      m1.index = 1
      m2.index = 2
      m3.index = 3
      m4.index = 4
      m5.index = 5
      m6.index = 6
      m7.index = 7
      m8.index = 8
      m9.index = 9
      m10.index = 10
      m11.index = 11
      m12.index = 12
      m13.index = 13
      m14.index = 14
      m15.index = 15
      m16.index = 16
      m17.index = 17
      m18.index = 18
      m19.index = 19
      m20.index = 20
      m21.index = 21
      m22.index = 22
      (m1.map(resultSet), m2.map(resultSet), m3.map(resultSet), m4.map(resultSet), m5.map(resultSet), m6.map(resultSet), m7.map(resultSet), m8.map(resultSet), m9.map(resultSet), m10.map(resultSet), m11.map(resultSet), m12.map(resultSet), m13.map(resultSet), m14.map(resultSet), m15.map(resultSet), m16.map(resultSet), m17.map(resultSet), m18.map(resultSet), m19.map(resultSet), m20.map(resultSet), m21.map(resultSet), m22.map(resultSet))
    }
  }
}
