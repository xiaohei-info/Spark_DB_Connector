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
      .insert().insert("col1", "col2")
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

## TODO LIST

- [ ] 在hbase-site.xml中设置hbase host并读取   
- [x] 将Scala集合/序列元素写入HBase   
- [ ] Scala集合/序列写入HBase时隐式读取hbase host
- [ ] Scala集合/序列写入Mysql时从conf中读取连接信息
- [ ] 写入Mysql时fitStatement隐式完成
- [ ] 读写HBase时添加salt特性   
- [ ] 添加Mysql的支持   
- [ ] 数据转换高级特性
- [ ] test


