package info.xiaohei.spark.connector.transformer.reader

import org.apache.hadoop.hbase.util.Bytes

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */

trait DataReaderConversions extends Serializable {

  // Simple types

  implicit def intReader: DataReader[Int] = new SingleColumnConcreteDataReader[Int] {
    def columnMap(cols: Array[Byte]): Int = Bytes.toInt(cols)
  }

  implicit def longReader: DataReader[Long] = new SingleColumnConcreteDataReader[Long] {
    def columnMap(cols: Array[Byte]): Long = Bytes.toLong(cols)
  }

  implicit def shortReader: DataReader[Short] = new SingleColumnConcreteDataReader[Short] {
    def columnMap(cols: Array[Byte]): Short = Bytes.toShort(cols)
  }

  implicit def doubleReader: DataReader[Double] = new SingleColumnConcreteDataReader[Double] {
    def columnMap(cols: Array[Byte]): Double = Bytes.toDouble(cols)
  }

  implicit def floatReader: DataReader[Float] = new SingleColumnConcreteDataReader[Float] {
    def columnMap(cols: Array[Byte]): Float = Bytes.toFloat(cols)
  }

  implicit def booleanReader: DataReader[Boolean] = new SingleColumnConcreteDataReader[Boolean] {
    def columnMap(cols: Array[Byte]): Boolean = Bytes.toBoolean(cols)
  }

  implicit def bigDecimalReader: DataReader[BigDecimal] = new SingleColumnConcreteDataReader[BigDecimal] {
    def columnMap(cols: Array[Byte]): BigDecimal = Bytes.toBigDecimal(cols)
  }

  implicit def stringReader: DataReader[String] = new SingleColumnConcreteDataReader[String] {
    def columnMap(cols: Array[Byte]): String = Bytes.toString(cols)
  }

  // Options

  implicit def optionReader[T](implicit c: DataReader[T]): DataReader[Option[T]] = new DataReader[Option[T]] {
    def transformHBaseData(data: HBaseData): Option[T] =
      if (data.size != 1) throw new IllegalArgumentException(s"Unexpected number of columns: expected 1, returned ${data.size}")
      else {
        if (!classOf[SingleColumnConcreteDataReader[T]].isAssignableFrom(c.getClass)) throw new IllegalArgumentException("Option[T] can be used only with primitive values")
        if (data.head.nonEmpty) Some(c.transformHBaseData(data))
        else None
      }
  }

  // Tuples

  implicit def tuple2Reader[T1, T2](implicit m1: DataReader[T1], m2: DataReader[T2]): DataReader[(T1, T2)] = new TupleDataReader[(T1, T2)] {

    val n = 2

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2))
    }
  }

  implicit def tuple3Reader[T1, T2, T3](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3]): DataReader[(T1, T2, T3)] = new TupleDataReader[(T1, T2, T3)] {

    val n = 3

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3))
    }
  }

  implicit def tuple4Reader[T1, T2, T3, T4](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4]): DataReader[(T1, T2, T3, T4)] = new TupleDataReader[(T1, T2, T3, T4)] {

    val n = 4

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4))
    }
  }

  implicit def tuple5Reader[T1, T2, T3, T4, T5](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5]): DataReader[(T1, T2, T3, T4, T5)] = new TupleDataReader[(T1, T2, T3, T4, T5)] {

    val n = 5

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5))
    }
  }

  implicit def tuple6Reader[T1, T2, T3, T4, T5, T6](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6]): DataReader[(T1, T2, T3, T4, T5, T6)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6)] {

    val n = 6

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6))
    }
  }

  implicit def tuple7Reader[T1, T2, T3, T4, T5, T6, T7](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7]): DataReader[(T1, T2, T3, T4, T5, T6, T7)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7)] {

    val n = 7

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7))
    }
  }

  implicit def tuple8Reader[T1, T2, T3, T4, T5, T6, T7, T8](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8)] {

    val n = 8

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8))
    }
  }

  implicit def tuple9Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] {

    val n = 9

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9))
    }
  }

  implicit def tuple10Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] {

    val n = 10

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10))
    }
  }

  implicit def tuple11Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] {

    val n = 11

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11))
    }
  }

  implicit def tuple12Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] {

    val n = 12

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12))
    }
  }

  implicit def tuple13Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] {

    val n = 13

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13))
    }
  }

  implicit def tuple14Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13], m14: DataReader[T14]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] {

    val n = 14

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      val h14 = data.slice(13, 14)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13), m14.transformHBaseData(h14))
    }
  }

  implicit def tuple15Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13], m14: DataReader[T14], m15: DataReader[T15]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] {

    val n = 15

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      val h14 = data.slice(13, 14)
      val h15 = data.slice(14, 15)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13), m14.transformHBaseData(h14), m15.transformHBaseData(h15))
    }
  }

  implicit def tuple16Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13], m14: DataReader[T14], m15: DataReader[T15], m16: DataReader[T16]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] {

    val n = 16

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      val h14 = data.slice(13, 14)
      val h15 = data.slice(14, 15)
      val h16 = data.slice(15, 16)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13), m14.transformHBaseData(h14), m15.transformHBaseData(h15), m16.transformHBaseData(h16))
    }
  }

  implicit def tuple17Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13], m14: DataReader[T14], m15: DataReader[T15], m16: DataReader[T16], m17: DataReader[T17]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] {

    val n = 17

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      val h14 = data.slice(13, 14)
      val h15 = data.slice(14, 15)
      val h16 = data.slice(15, 16)
      val h17 = data.slice(16, 17)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13), m14.transformHBaseData(h14), m15.transformHBaseData(h15), m16.transformHBaseData(h16), m17.transformHBaseData(h17))
    }
  }

  implicit def tuple18Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13], m14: DataReader[T14], m15: DataReader[T15], m16: DataReader[T16], m17: DataReader[T17], m18: DataReader[T18]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] {

    val n = 18

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      val h14 = data.slice(13, 14)
      val h15 = data.slice(14, 15)
      val h16 = data.slice(15, 16)
      val h17 = data.slice(16, 17)
      val h18 = data.slice(17, 18)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13), m14.transformHBaseData(h14), m15.transformHBaseData(h15), m16.transformHBaseData(h16), m17.transformHBaseData(h17), m18.transformHBaseData(h18))
    }
  }

  implicit def tuple19Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13], m14: DataReader[T14], m15: DataReader[T15], m16: DataReader[T16], m17: DataReader[T17], m18: DataReader[T18], m19: DataReader[T19]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] {

    val n = 19

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      val h14 = data.slice(13, 14)
      val h15 = data.slice(14, 15)
      val h16 = data.slice(15, 16)
      val h17 = data.slice(16, 17)
      val h18 = data.slice(17, 18)
      val h19 = data.slice(18, 19)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13), m14.transformHBaseData(h14), m15.transformHBaseData(h15), m16.transformHBaseData(h16), m17.transformHBaseData(h17), m18.transformHBaseData(h18), m19.transformHBaseData(h19))
    }
  }

  implicit def tuple20Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13], m14: DataReader[T14], m15: DataReader[T15], m16: DataReader[T16], m17: DataReader[T17], m18: DataReader[T18], m19: DataReader[T19], m20: DataReader[T20]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] {

    val n = 20

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      val h14 = data.slice(13, 14)
      val h15 = data.slice(14, 15)
      val h16 = data.slice(15, 16)
      val h17 = data.slice(16, 17)
      val h18 = data.slice(17, 18)
      val h19 = data.slice(18, 19)
      val h20 = data.slice(19, 20)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13), m14.transformHBaseData(h14), m15.transformHBaseData(h15), m16.transformHBaseData(h16), m17.transformHBaseData(h17), m18.transformHBaseData(h18), m19.transformHBaseData(h19), m20.transformHBaseData(h20))
    }
  }

  implicit def tuple21Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13], m14: DataReader[T14], m15: DataReader[T15], m16: DataReader[T16], m17: DataReader[T17], m18: DataReader[T18], m19: DataReader[T19], m20: DataReader[T20], m21: DataReader[T21]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] {

    val n = 21

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      val h14 = data.slice(13, 14)
      val h15 = data.slice(14, 15)
      val h16 = data.slice(15, 16)
      val h17 = data.slice(16, 17)
      val h18 = data.slice(17, 18)
      val h19 = data.slice(18, 19)
      val h20 = data.slice(19, 20)
      val h21 = data.slice(20, 21)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13), m14.transformHBaseData(h14), m15.transformHBaseData(h15), m16.transformHBaseData(h16), m17.transformHBaseData(h17), m18.transformHBaseData(h18), m19.transformHBaseData(h19), m20.transformHBaseData(h20), m21.transformHBaseData(h21))
    }
  }

  implicit def tuple22Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22](implicit m1: DataReader[T1], m2: DataReader[T2], m3: DataReader[T3], m4: DataReader[T4], m5: DataReader[T5], m6: DataReader[T6], m7: DataReader[T7], m8: DataReader[T8], m9: DataReader[T9], m10: DataReader[T10], m11: DataReader[T11], m12: DataReader[T12], m13: DataReader[T13], m14: DataReader[T14], m15: DataReader[T15], m16: DataReader[T16], m17: DataReader[T17], m18: DataReader[T18], m19: DataReader[T19], m20: DataReader[T20], m21: DataReader[T21], m22: DataReader[T22]): DataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] = new TupleDataReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] {

    val n = 22

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      val h8 = data.slice(7, 8)
      val h9 = data.slice(8, 9)
      val h10 = data.slice(9, 10)
      val h11 = data.slice(10, 11)
      val h12 = data.slice(11, 12)
      val h13 = data.slice(12, 13)
      val h14 = data.slice(13, 14)
      val h15 = data.slice(14, 15)
      val h16 = data.slice(15, 16)
      val h17 = data.slice(16, 17)
      val h18 = data.slice(17, 18)
      val h19 = data.slice(18, 19)
      val h20 = data.slice(19, 20)
      val h21 = data.slice(20, 21)
      val h22 = data.slice(21, 22)
      (m1.transformHBaseData(h1), m2.transformHBaseData(h2), m3.transformHBaseData(h3), m4.transformHBaseData(h4), m5.transformHBaseData(h5), m6.transformHBaseData(h6), m7.transformHBaseData(h7), m8.transformHBaseData(h8), m9.transformHBaseData(h9), m10.transformHBaseData(h10), m11.transformHBaseData(h11), m12.transformHBaseData(h12), m13.transformHBaseData(h13), m14.transformHBaseData(h14), m15.transformHBaseData(h15), m16.transformHBaseData(h16), m17.transformHBaseData(h17), m18.transformHBaseData(h18), m19.transformHBaseData(h19), m20.transformHBaseData(h20), m21.transformHBaseData(h21), m22.transformHBaseData(h22))
    }
  }
}


