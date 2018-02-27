package info.xiaohei.spark.connector.utils

import scalaj.http.{Http, HttpOptions}

/**
  * Author: xiaohei
  * Date: 2017/8/30
  * Email: xiaohei.info@gmail.com
  * Host: xiaohei.info
  */
object NetUtil {

  def wechatAlert(content: String) = {
    Http("http://192.168.32.25:9099/warnnerNotice/sendWeixin").postData(s"""{"groupId":"1000011","content":"$content"}""")
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString
  }

}
