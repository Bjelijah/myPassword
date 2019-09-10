package xyz.mercs.myPassword

import android.content.Context
import android.content.pm.PackageManager
import xyz.mercs.libnet.helper.HeaderInterceptor

object Config {
    var URL:String?=null
    var VERSION:String = "0.0.0"
    var BUILD_LEVEL  =0
    fun init(c: Context){
        var appInfo = c.packageManager.getApplicationInfo(c.packageName, PackageManager.GET_META_DATA)
        BUILD_LEVEL = appInfo.metaData.getInt("BUILD_LEVEL")

        when(BUILD_LEVEL){
            0->{//开发
                URL ="http://wiki.mercs.xyz:8123/mock/54/"
            }
            1->{//测试
                URL =""
            }
            2->{//生产
             //   URL ="http://chk.surea.cn:9007/AppWebApi/api/AppWebApi/"
                URL =""
            }
        }
        VERSION = try {
            c.packageManager.getPackageInfo(c.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "0.0.0"
        }
        HeaderInterceptor.API_VERSION = VERSION
    }
}