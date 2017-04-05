# Spark Database Conntector

隐藏处理各种数据库的连接细节，使用Scala API在Spark中简易地处理数据库连接的读写操作。

## HBase

### 设置HBase host

通过以下三种任意方式设置HBase host地址

**1、在spark-submit中设置命令：**

```
spark-submit --conf spark.hbase.host=your-hbase-host
```

**2、在Scala代码中设置：**

```
val sparkConf = new SparkConf()
sparkConf.set("spark.hbase.host", "your-hbase-host")
val sc = new SparkContext(sparkConf)
```

**3、在JVM参数中设置：**

```
java -Dspark.hbase.host=your-hbase-host -jar ....
```

**设置hbase-site.xml文件读取路径(可选)**

如果有读取hbase-site.xml文件的需求时,可以通过设置下面的选项进行指定:

```
spark.hbase.config=your-hbase-config-path
```

设置该选项的方式同上
注意:需要将hbase-site.xml文件添加到当前项目可识别的resource路径中,否则将无法读取,使用默认配置

### 向HBase写入数据

**导入隐式转换：**

```
import info.xiaohei.spark.connector.hbase._
```

#### Spark RDD写入HBase

任何Spark RDD对象都能直接操作写入HBase，例如：

```
val rdd = sc.parallelize(1 to 100)
            .map(i => (s"rowkey-${i.toString}", s"column1-${i.toString}", "column2"))
```

这个RDD包含了100个三元组类型的数据，写入HBase时，第一个元素为rowkey，剩下的元素依次为各个列的值：

```
rdd.toHBase("mytable")
      .insert("col1", "col2")
      .inColumnFamily("columnFamily")
      .save()
```

(1)使用RDD的toHBase函数传入要写入的表名   
(2)insert函数传入要插入的各个列名   
(3)inColumnFamily函数传入这些列所在的列族名   
(4)最后save函数将该RDD保存在HBase中

如果col2和col1的列族不一样，可以在insert传入列名时单独指定：

```
rdd.toHBase("mytable")
      .insert("col1", "otherColumnFamily:col2")
      .inColumnFamily("defaultColumnFamily")
      .save()
```

列族名和列名之间要用冒号(:)隔开，其他列需要指定列名时使用的方式一致

#### Scala集合/序列写入HBase

```
val dataList  = Seq[(String, String)](
      ("00001475304346643896037", "kgJkm0euSbe"),
      ("00001475376619355219953", "kiaR40qzI8o"),
      ("00001475458728618943637", "kgCoW0hgzXO"),
      ("00001475838363931738019", "kqiHu0WNJC0")

    )

dataList.toHBase("your-hbase-host", "mytable")
	.insert("col1", "col2")
	.inColumnFamily("columnFamily")
	.save()
```

使用方式和RDD写入HBase的操作类似，唯一不同的是需要额外指定hbase host的值（此处待完善）

以上的方式将使用HTable的put list批量将集合中的数据一次全部put到HBase中，如果写入HBase时想使用缓存区的方式，需要另外添加几个参数：

```
dataList.toHBase("your-hbase-host"
      , "mytable"
      //该参数指定写入时的autoFlush为false
      , Some(false, false)
      //该参数指定写入缓冲区的大小
      , Some(5 * 1024 * 1024))
      .insert("col1", "col2")
      .inColumnFamily("columnFamily")
      .save()
```

使用该方式时，集合中的每个数据都会被put一次，但是关闭了自动刷写，所以只有当缓冲区满了之后才会批量向HBase写入

### 读取HBase数据

**导入隐式转换：**

```
import info.xiaohei.spark.connector.hbase._
```

读取HBase的数据操作需要通过sc来进行：

```
val hbaseRdd = sc.fromHBase[(String, String, String)]("mytable")
      .select("col1", "col2")
      .inColumnFamily("columnFamily")
      .withStartRow("startRow")
      .withEndRow("endRow")
```

(1)使用sc的fromHBase函数传入要读取数据的表名，该函数需要指定读取数据的类型信息   
(2)select函数传入要读取的各个列名   
(3)inColumnFamily函数传入这些列所在的列族名   
(4)withStartRow和withEndRow将设置rowkey的扫描范围，可选操作
(5)之后就可以在hbaseRdd上执行Spark RDD的各种算子操作

上面的例子中，fromHBase的泛型类型为三元组，但是select中只读取了两列值，因此，该三元组中第一个元素将是rowkey的值，其他元素按照列的顺序依次类推   

当你不需要读取rowkey的值时，只需要将fromHBase的泛型类型改为二元组

即读取的列数为n，泛型类型为n元组时，列名和元组中的各个元素相对应
读取的列数为n，泛型类型为n+1元组时，元组的第一个元素为rowkey

当各个列位于不同列族时，设置列族的方式同写入HBase一致

### SQL On HBase

借助SQLContext的DataFrame接口，在组件中可以轻易实现SQL On HBase的功能。

上例中的hbaseRdd是从HBase中读取出来的数据，在此RDD的基础上进行转换操作：

```
//创建org.apache.spark.sql.Row类型的RDD
val rowRdd = hbaseRdd.map(r => Row(r._1, r._2, r._3))
val sqlContext = new SQLContext(sc)
val df = sqlContext.createDataFrame(
      rowRdd,
      StructType(Array(StructField("col1", StringType), StructField("col2", StringType), StructField("col3", StringType)))
    )
df.show()

df.registerTempTable("mytable")
sqlContext.sql("select col1 from mytable").show()
```

### 使用case class查询/读取HBase的数据

使用内置的隐式转换可以处理基本数据类型和元组数据,当有使用case class的需求时,需要额外做一些准备工作

定义如下的case class:

```
case class MyClass(name: String, age: Int)
```

如果想达到以下的效果:

```
val classRdd = sc.fromHBase[MyClass]("tableName")
    .select("name","age")
    .inColumnFamily("info")

classRdd.map{
    c =>
        (c.name,c.age)
}
```

或者以下的效果:

```
//classRdd的类型为RDD[MyClass]
classRdd.toHBase("tableName")
    .insert("name","age")
    .inColumnFamily("info")
    .save()
```

需要另外实现能够解析自定义case class的隐式方法:

```
implicit def MyReaderConversion: DataReader[MyClass] = new CustomDataReader[(String, Int), MyClass] {
    override def convert(data: (String, Int)): MyClass = MyClass(data._1, data._2)
  }

implicit def MyWriterConversion: DataWriter[MyClass] = new CustomDataWriter[MyClass, (String, Int)] {
    override def convert(data: MyClass): (String, Int) = (data.name, data.age)
  }
```

该隐式方法返回一个DataReader/DataWriter 重写CustomDataReader/CustomDataWriter中的convert方法
将case class转换为一个元组或者将元组转化为case class即可

## TODO LIST

- [x] 在hbase-site.xml中设置hbase host并读取
- [x] 将Scala集合/序列元素写入HBase
- [x] 自定义case class的解析
- [ ] Scala集合/序列写入HBase时隐式读取hbase host
- [ ] 读写HBase时添加salt特性
- [x] 添加Mysql的支持
- [ ] Scala集合/序列写入Mysql时从conf中读取连接信息
- [ ] 写入Mysql时fitStatement隐式完成
- [ ] 数据转换高级特性/统一接口


