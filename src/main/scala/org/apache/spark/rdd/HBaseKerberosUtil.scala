package org.apache.spark.rdd

import java.io.IOException
import java.security.PrivilegedExceptionAction

import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.util.SerializableConfiguration

/**
  * Author: xiaohei
  * Date: 2017/7/7
  * Email: xiaohei.info@gmail.com
  * Host: xiaohei.info
  */
object HBaseKerberosUtil {
  @throws[IOException]
  def ugiDoAs[A](conf: SerializableConfiguration, func: () => A): A = {
    UserGroupInformation.setConfiguration(conf.value)
    val ugi: UserGroupInformation = UserGroupInformation
      .loginUserFromKeytabAndReturnUGI("hpt", "/app/hpt.keytab")
    UserGroupInformation.setLoginUser(ugi)
    ugi.checkTGTAndReloginFromKeytab()
    ugi.doAs(new PrivilegedExceptionAction[A] {
      def run: A = {
        func()
      }
    })
  }
}
