package xyz.mercs.AresAndroid

import android.app.Application
import android.content.pm.PackageManager.NameNotFoundException
import com.alibaba.android.arouter.launcher.ARouter
import xyz.mercs.AresAndroid.BuildConfig
import xyz.mercs.libnet.HttpManager
import xyz.mercs.libnet.helper.HeaderInterceptor
import xyz.mercs.ryfddandroid.Config


class APP : Application() {

    override fun onCreate() {
        super.onCreate()
        Config.init(this)

        HttpManager
            .instance
            .addInterceptor(HeaderInterceptor())
            .initHttpClient(applicationContext,Config.URL!!,false)

        if (BuildConfig.DEBUG){
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }

}