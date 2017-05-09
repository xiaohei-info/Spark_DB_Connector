package info.xiaohei.spark.connector.hbase.transformer.writer

import org.apache.hadoop.hbase.util.Bytes

/**
  * Author: xiaohei
  * Date: 2017/3/21
  * Email: xiaohei.info@gmail.com
  * Host: www.xiaohei.info
  */
trait DataWriterConversions extends Serializable{
  implicit def intWriter: DataWriter[Int] = new SingleColumnDataWriter[Int] {
    override def writeSingleColumn(data: Int): Option[Array[Byte]] = Some(Bytes.toBytes(data))
  }

  implicit def longWriter: DataWriter[Long] = new SingleColumnDataWriter[Long] {
    override def writeSingleColumn(data: Long): Option[Array[Byte]] = Some(Bytes.toBytes(data))
  }

  implicit def shortWriter: DataWriter[Short] = new SingleColumnDataWriter[Short] {
    override def writeSingleColumn(data: Short): Option[Array[Byte]] = Some(Bytes.toBytes(data))
  }

  implicit def doubleWriter: DataWriter[Double] = new SingleColumnDataWriter[Double] {
    override def writeSingleColumn(data: Double): Option[Array[Byte]] = Some(Bytes.toBytes(data))
  }

  implicit def floatWriter: DataWriter[Float] = new SingleColumnDataWriter[Float] {
    override def writeSingleColumn(data: Float): Option[Array[Byte]] = Some(Bytes.toBytes(data))
  }

  implicit def booleanWriter: DataWriter[Boolean] = new SingleColumnDataWriter[Boolean] {
    override def writeSingleColumn(data: Boolean): Option[Array[Byte]] = Some(Bytes.toBytes(data))
  }

  implicit def bigDecimalWriter: DataWriter[BigDecimal] = new SingleColumnDataWriter[BigDecimal] {
    override def writeSingleColumn(data: BigDecimal): Option[Array[Byte]] = Some(Bytes.toBytes(data.bigDecimal))
  }

  implicit def stringWriter: DataWriter[String] = new SingleColumnDataWriter[String] {
    override def writeSingleColumn(data: String): Option[Array[Byte]] = Some(Bytes.toBytes(data))
  }

  // Options

  implicit def optionWriter[T](implicit c: DataWriter[T]): DataWriter[Option[T]] = new DataWriter[Option[T]] {
    override def write(data: Option[T]): HBaseData = if(data.nonEmpty) c.write(data.get) else Seq(None)
  }

  // Tuples


  implicit def tupleWriter2[T1, T2](implicit c1: DataWriter[T1], c2: DataWriter[T2]): DataWriter[(T1, T2)] = new DataWriter[(T1, T2)] {
    override def write(data: (T1, T2)): HBaseData = c1.write(data._1) ++ c2.write(data._2)
  }

  implicit def tupleWriter3[T1, T2, T3](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3]): DataWriter[(T1, T2, T3)] = new DataWriter[(T1, T2, T3)] {
    override def write(data: (T1, T2, T3)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3)
  }

  implicit def tupleWriter4[T1, T2, T3, T4](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4]): DataWriter[(T1, T2, T3, T4)] = new DataWriter[(T1, T2, T3, T4)] {
    override def write(data: (T1, T2, T3, T4)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4)
  }

  implicit def tupleWriter5[T1, T2, T3, T4, T5](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5]): DataWriter[(T1, T2, T3, T4, T5)] = new DataWriter[(T1, T2, T3, T4, T5)] {
    override def write(data: (T1, T2, T3, T4, T5)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5)
  }

  implicit def tupleWriter6[T1, T2, T3, T4, T5, T6](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6]): DataWriter[(T1, T2, T3, T4, T5, T6)] = new DataWriter[(T1, T2, T3, T4, T5, T6)] {
    override def write(data: (T1, T2, T3, T4, T5, T6)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6)
  }

  implicit def tupleWriter7[T1, T2, T3, T4, T5, T6, T7](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7]): DataWriter[(T1, T2, T3, T4, T5, T6, T7)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7)
  }

  implicit def tupleWriter8[T1, T2, T3, T4, T5, T6, T7, T8](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8)
  }

  implicit def tupleWriter9[T1, T2, T3, T4, T5, T6, T7, T8, T9](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9)
  }

  implicit def tupleWriter10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10)
  }

  implicit def tupleWriter11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11)
  }

  implicit def tupleWriter12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12)
  }

  implicit def tupleWriter13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13)
  }

  implicit def tupleWriter14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13], c14: DataWriter[T14]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13) ++ c14.write(data._14)
  }

  implicit def tupleWriter15[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13], c14: DataWriter[T14], c15: DataWriter[T15]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13) ++ c14.write(data._14) ++ c15.write(data._15)
  }

  implicit def tupleWriter16[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13], c14: DataWriter[T14], c15: DataWriter[T15], c16: DataWriter[T16]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13) ++ c14.write(data._14) ++ c15.write(data._15) ++ c16.write(data._16)
  }

  implicit def tupleWriter17[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13], c14: DataWriter[T14], c15: DataWriter[T15], c16: DataWriter[T16], c17: DataWriter[T17]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13) ++ c14.write(data._14) ++ c15.write(data._15) ++ c16.write(data._16) ++ c17.write(data._17)
  }

  implicit def tupleWriter18[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13], c14: DataWriter[T14], c15: DataWriter[T15], c16: DataWriter[T16], c17: DataWriter[T17], c18: DataWriter[T18]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13) ++ c14.write(data._14) ++ c15.write(data._15) ++ c16.write(data._16) ++ c17.write(data._17) ++ c18.write(data._18)
  }

  implicit def tupleWriter19[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13], c14: DataWriter[T14], c15: DataWriter[T15], c16: DataWriter[T16], c17: DataWriter[T17], c18: DataWriter[T18], c19: DataWriter[T19]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13) ++ c14.write(data._14) ++ c15.write(data._15) ++ c16.write(data._16) ++ c17.write(data._17) ++ c18.write(data._18) ++ c19.write(data._19)
  }

  implicit def tupleWriter20[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13], c14: DataWriter[T14], c15: DataWriter[T15], c16: DataWriter[T16], c17: DataWriter[T17], c18: DataWriter[T18], c19: DataWriter[T19], c20: DataWriter[T20]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13) ++ c14.write(data._14) ++ c15.write(data._15) ++ c16.write(data._16) ++ c17.write(data._17) ++ c18.write(data._18) ++ c19.write(data._19) ++ c20.write(data._20)
  }

  implicit def tupleWriter21[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13], c14: DataWriter[T14], c15: DataWriter[T15], c16: DataWriter[T16], c17: DataWriter[T17], c18: DataWriter[T18], c19: DataWriter[T19], c20: DataWriter[T20], c21: DataWriter[T21]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13) ++ c14.write(data._14) ++ c15.write(data._15) ++ c16.write(data._16) ++ c17.write(data._17) ++ c18.write(data._18) ++ c19.write(data._19) ++ c20.write(data._20) ++ c21.write(data._21)
  }

  implicit def tupleWriter22[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22](implicit c1: DataWriter[T1], c2: DataWriter[T2], c3: DataWriter[T3], c4: DataWriter[T4], c5: DataWriter[T5], c6: DataWriter[T6], c7: DataWriter[T7], c8: DataWriter[T8], c9: DataWriter[T9], c10: DataWriter[T10], c11: DataWriter[T11], c12: DataWriter[T12], c13: DataWriter[T13], c14: DataWriter[T14], c15: DataWriter[T15], c16: DataWriter[T16], c17: DataWriter[T17], c18: DataWriter[T18], c19: DataWriter[T19], c20: DataWriter[T20], c21: DataWriter[T21], c22: DataWriter[T22]): DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] = new DataWriter[(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)] {
    override def write(data: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)): HBaseData = c1.write(data._1) ++ c2.write(data._2) ++ c3.write(data._3) ++ c4.write(data._4) ++ c5.write(data._5) ++ c6.write(data._6) ++ c7.write(data._7) ++ c8.write(data._8) ++ c9.write(data._9) ++ c10.write(data._10) ++ c11.write(data._11) ++ c12.write(data._12) ++ c13.write(data._13) ++ c14.write(data._14) ++ c15.write(data._15) ++ c16.write(data._16) ++ c17.write(data._17) ++ c18.write(data._18) ++ c19.write(data._19) ++ c20.write(data._20) ++ c21.write(data._21) ++ c22.write(data._22)
  }

}
