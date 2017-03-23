package info.xiaohei.spark.connector.hbase.reader

import info.xiaohei.spark.connector.hbase.FieldMapper
import org.apache.hadoop.hbase.util.Bytes

trait FieldReader[T] extends FieldMapper {
  def revertFromHBaseData(data: HBaseData): T
}

/**
  * Utility class used to simplify the creation of custom mappers.
  * FieldReaderProxy's can reuse predefined FieldReader's.
  */
//abstract class FieldReaderProxy[P, T](implicit reader: FieldReader[P]) extends FieldReader[T] {
//
//  override def map(data: HBaseData): T = convert(reader.map(data))
//
//  def convert(data: P): T
//}

trait SingleColumnFieldReader[T] extends FieldReader[T] {

  def revertFromHBaseData(data: HBaseData): T =
    if (data.size == 1)
      columnMapWithOption(data.head)
    else if (data.size == 2)
      columnMapWithOption(data.drop(1).head)
    else
      throw new IllegalArgumentException(s"Unexpected number of columns: expected 1 or 2, returned ${data.size}")

  def columnMapWithOption(cols: Option[Array[Byte]]): T
}

trait SingleColumnConcreteFieldReader[T] extends SingleColumnFieldReader[T] {

  def columnMapWithOption(cols: Option[Array[Byte]]) =
    if (cols.nonEmpty) columnMap(cols.get)
    else throw new IllegalArgumentException("Null value assigned to concrete class. Use Option[T] instead")

  def columnMap(cols: Array[Byte]): T
}

trait TupleFieldReader[T <: Product] extends FieldReader[T] {

  val n: Int

  def revertFromHBaseData(data: HBaseData): T =
    if (data.size == n)
      tupleMap(data)
    else if (data.size == n + 1)
      tupleMap(data.drop(1))
    else
      throw new IllegalArgumentException(s"Unexpected number of columns: expected $n or ${n - 1}, returned ${data.size}")

  def tupleMap(data: HBaseData): T
}

trait FieldReaderConversions extends Serializable {

  // Simple types

  implicit def intReader: FieldReader[Int] = new SingleColumnConcreteFieldReader[Int] {
    def columnMap(cols: Array[Byte]): Int = Bytes.toInt(cols)
  }

  implicit def longReader: FieldReader[Long] = new SingleColumnConcreteFieldReader[Long] {
    def columnMap(cols: Array[Byte]): Long = Bytes.toLong(cols)
  }

  implicit def shortReader: FieldReader[Short] = new SingleColumnConcreteFieldReader[Short] {
    def columnMap(cols: Array[Byte]): Short = Bytes.toShort(cols)
  }

  implicit def doubleReader: FieldReader[Double] = new SingleColumnConcreteFieldReader[Double] {
    def columnMap(cols: Array[Byte]): Double = Bytes.toDouble(cols)
  }

  implicit def floatReader: FieldReader[Float] = new SingleColumnConcreteFieldReader[Float] {
    def columnMap(cols: Array[Byte]): Float = Bytes.toFloat(cols)
  }

  implicit def booleanReader: FieldReader[Boolean] = new SingleColumnConcreteFieldReader[Boolean] {
    def columnMap(cols: Array[Byte]): Boolean = Bytes.toBoolean(cols)
  }

  implicit def bigDecimalReader: FieldReader[BigDecimal] = new SingleColumnConcreteFieldReader[BigDecimal] {
    def columnMap(cols: Array[Byte]): BigDecimal = Bytes.toBigDecimal(cols)
  }

  implicit def stringReader: FieldReader[String] = new SingleColumnConcreteFieldReader[String] {
    def columnMap(cols: Array[Byte]): String = Bytes.toString(cols)
  }

  // Options

  implicit def optionReader[T](implicit c: FieldReader[T]): FieldReader[Option[T]] = new FieldReader[Option[T]] {
    def revertFromHBaseData(data: HBaseData): Option[T] =
      if (data.size != 1) throw new IllegalArgumentException(s"Unexpected number of columns: expected 1, returned ${data.size}")
      else {
        if (!classOf[SingleColumnConcreteFieldReader[T]].isAssignableFrom(c.getClass)) throw new IllegalArgumentException("Option[T] can be used only with primitive values")
        if (data.head.nonEmpty) Some(c.revertFromHBaseData(data))
        else None
      }
  }

  // Tuples

  implicit def tuple2Reader[T1, T2](implicit m1: FieldReader[T1], m2: FieldReader[T2]): FieldReader[(T1, T2)] = new TupleFieldReader[(T1, T2)] {

    val n = 2

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2))
    }
  }

  implicit def tuple3Reader[T1, T2, T3](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3]): FieldReader[(T1, T2, T3)] = new TupleFieldReader[(T1, T2, T3)] {

    val n = 3

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3))
    }
  }

  implicit def tuple4Reader[T1, T2, T3, T4](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4]): FieldReader[(T1, T2, T3, T4)] = new TupleFieldReader[(T1, T2, T3, T4)] {

    val n = 4

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4))
    }
  }

  implicit def tuple5Reader[T1, T2, T3, T4, T5](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5]): FieldReader[(T1, T2, T3, T4, T5)] = new TupleFieldReader[(T1, T2, T3, T4, T5)] {

    val n = 5

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5))
    }
  }

  implicit def tuple6Reader[T1, T2, T3, T4, T5, T6](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6]): FieldReader[(T1, T2, T3, T4, T5, T6)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6)] {

    val n = 6

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6))
    }
  }

  implicit def tuple7Reader[T1, T2, T3, T4, T5, T6, T7](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7]): FieldReader[(T1, T2, T3, T4, T5, T6, T7)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7)] {

    val n = 7

    def tupleMap(data: HBaseData) = {
      val h1 = data.take(1)
      val h2 = data.slice(1, 2)
      val h3 = data.slice(2, 3)
      val h4 = data.slice(3, 4)
      val h5 = data.slice(4, 5)
      val h6 = data.slice(5, 6)
      val h7 = data.slice(6, 7)
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7))
    }
  }

  implicit def tuple8Reader[T1, T2, T3, T4, T5, T6, T7, T8](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8))
    }
  }

  implicit def tuple9Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9))
    }
  }

  implicit def tuple10Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10))
    }
  }

  implicit def tuple11Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11))
    }
  }

  implicit def tuple12Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12))
    }
  }

  implicit def tuple13Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13))
    }
  }

  implicit def tuple14Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13], m14: FieldReader[T14]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13), m14.revertFromHBaseData(h14))
    }
  }

  implicit def tuple15Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13], m14: FieldReader[T14], m15: FieldReader[T15]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13), m14.revertFromHBaseData(h14), m15.revertFromHBaseData(h15))
    }
  }

  implicit def tuple16Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13], m14: FieldReader[T14], m15: FieldReader[T15], m16: FieldReader[T16]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13), m14.revertFromHBaseData(h14), m15.revertFromHBaseData(h15), m16.revertFromHBaseData(h16))
    }
  }

  implicit def tuple17Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13], m14: FieldReader[T14], m15: FieldReader[T15], m16: FieldReader[T16], m17: FieldReader[T17]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13), m14.revertFromHBaseData(h14), m15.revertFromHBaseData(h15), m16.revertFromHBaseData(h16), m17.revertFromHBaseData(h17))
    }
  }

  implicit def tuple18Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13], m14: FieldReader[T14], m15: FieldReader[T15], m16: FieldReader[T16], m17: FieldReader[T17], m18: FieldReader[T18]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13), m14.revertFromHBaseData(h14), m15.revertFromHBaseData(h15), m16.revertFromHBaseData(h16), m17.revertFromHBaseData(h17), m18.revertFromHBaseData(h18))
    }
  }

  implicit def tuple19Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13], m14: FieldReader[T14], m15: FieldReader[T15], m16: FieldReader[T16], m17: FieldReader[T17], m18: FieldReader[T18], m19: FieldReader[T19]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13), m14.revertFromHBaseData(h14), m15.revertFromHBaseData(h15), m16.revertFromHBaseData(h16), m17.revertFromHBaseData(h17), m18.revertFromHBaseData(h18), m19.revertFromHBaseData(h19))
    }
  }

  implicit def tuple20Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13], m14: FieldReader[T14], m15: FieldReader[T15], m16: FieldReader[T16], m17: FieldReader[T17], m18: FieldReader[T18], m19: FieldReader[T19], m20: FieldReader[T20]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13), m14.revertFromHBaseData(h14), m15.revertFromHBaseData(h15), m16.revertFromHBaseData(h16), m17.revertFromHBaseData(h17), m18.revertFromHBaseData(h18), m19.revertFromHBaseData(h19), m20.revertFromHBaseData(h20))
    }
  }

  implicit def tuple21Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13], m14: FieldReader[T14], m15: FieldReader[T15], m16: FieldReader[T16], m17: FieldReader[T17], m18: FieldReader[T18], m19: FieldReader[T19], m20: FieldReader[T20], m21: FieldReader[T21]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13), m14.revertFromHBaseData(h14), m15.revertFromHBaseData(h15), m16.revertFromHBaseData(h16), m17.revertFromHBaseData(h17), m18.revertFromHBaseData(h18), m19.revertFromHBaseData(h19), m20.revertFromHBaseData(h20), m21.revertFromHBaseData(h21))
    }
  }

  implicit def tuple22Reader[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22](implicit m1: FieldReader[T1], m2: FieldReader[T2], m3: FieldReader[T3], m4: FieldReader[T4], m5: FieldReader[T5], m6: FieldReader[T6], m7: FieldReader[T7], m8: FieldReader[T8], m9: FieldReader[T9], m10: FieldReader[T10], m11: FieldReader[T11], m12: FieldReader[T12], m13: FieldReader[T13], m14: FieldReader[T14], m15: FieldReader[T15], m16: FieldReader[T16], m17: FieldReader[T17], m18: FieldReader[T18], m19: FieldReader[T19], m20: FieldReader[T20], m21: FieldReader[T21], m22: FieldReader[T22]): FieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] = new TupleFieldReader[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] {

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
      (m1.revertFromHBaseData(h1), m2.revertFromHBaseData(h2), m3.revertFromHBaseData(h3), m4.revertFromHBaseData(h4), m5.revertFromHBaseData(h5), m6.revertFromHBaseData(h6), m7.revertFromHBaseData(h7), m8.revertFromHBaseData(h8), m9.revertFromHBaseData(h9), m10.revertFromHBaseData(h10), m11.revertFromHBaseData(h11), m12.revertFromHBaseData(h12), m13.revertFromHBaseData(h13), m14.revertFromHBaseData(h14), m15.revertFromHBaseData(h15), m16.revertFromHBaseData(h16), m17.revertFromHBaseData(h17), m18.revertFromHBaseData(h18), m19.revertFromHBaseData(h19), m20.revertFromHBaseData(h20), m21.revertFromHBaseData(h21), m22.revertFromHBaseData(h22))
    }
  }
}










