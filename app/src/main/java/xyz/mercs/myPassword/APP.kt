package xyz.mercs.myPassword

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import xyz.mercs.libnet.HttpManager
import xyz.mercs.libnet.helper.HeaderInterceptor


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