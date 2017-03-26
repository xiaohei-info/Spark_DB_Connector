package info.xiaohei.spark.connector.transformer.reader

/**
  * Author: xiaohei
  * Date: 2017/3/26
  * Email: yuande.jiang@fugetech.com
  * Host: xiaohei.info
  */
trait TupleDataReader[T <: Product] extends DataReader[T] {

  val n: Int

  def transformHBaseData(data: HBaseData): T =
    if (data.size == n)
      tupleMap(data)
    else if (data.size == n + 1)
      tupleMap(data.drop(1))
    else
      throw new IllegalArgumentException(s"Unexpected number of columns: expected $n or ${n - 1}, returned ${data.size}")

  def tupleMap(data: HBaseData): T
}

