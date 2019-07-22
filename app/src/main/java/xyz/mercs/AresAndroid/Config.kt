package xyz.mercs.ryfddandroid

import android.content.Context
import android.content.pm.PackageManager
import xyz.mercs.AresAndroid.BuildConfig

object Config {
    var URL:String?=null
    var VERSION:String = "0.0.0"
    fun init(c: Context){
        when(BuildConfig.build_level){
            0->{//开发
                URL ="http://wiki.mercs.xyz:8123/mock/54/"
            }
            1->{//测试
                URL ="http://ryfdd.mercs.xyz:8897/AppWebApi/api/AppWebApi/"
            }
            2->{//生产
             //   URL ="http://chk.surea.cn:9007/AppWebApi/api/AppWebApi/"
                URL ="http://rymtg.rysjamc.com:9007/AppWebApi/api/AppWebApi/"
            }

        }
        VERSION = try {
            c.packageManager.getPackageInfo(c.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "0.0.0"
        }

    }







}