package xyz.mercs.ryfddandroid

import android.app.Application
import android.content.pm.PackageManager.NameNotFoundException
import com.alibaba.android.arouter.launcher.ARouter
import xyz.mercs.AresAndroid.BuildConfig
import xyz.mercs.libnet.HttpManager
import xyz.mercs.libnet.utils.HeaderInterceptor


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

    private fun getLocalVersionName():String{
        var localVersion = ""
        try{
            var packageInfo = packageManager
                .getPackageInfo(packageName,0)
            localVersion = packageInfo.versionName
        }catch (e: NameNotFoundException){
            e.printStackTrace()
        }
        return localVersion
    }

}