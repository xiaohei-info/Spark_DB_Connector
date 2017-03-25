# Spark Database Conntector

隐藏处理各种数据库的连接细节，使用Scala API在Spark中简易地处理数据库连接的读写操作。

## HBase

### 设置HBase host

通过以下三种任意方式设置HBase host地址

**1、在spark-submit中设置命令：**

``
spark-submit --conf spark.hbase.host=your-hbase-host
``

**2、在Scala代码中设置：**

``
val sparkConf = new SparkConf()
sparkConf.set("spark.hbase.host", "your-hbase-host")
val sc = new SparkContext(sparkConf)
``

**3、在JVM参数中设置：**

``
java -Dspark.hbase.host=your-hbase-host -jar ....
``

- [ ] 在hbase-site.xml中设置并读取

### 读取HBase数据

**导入隐式转换：**

``
import info.xiaohei.spark.connector.hbase._
``