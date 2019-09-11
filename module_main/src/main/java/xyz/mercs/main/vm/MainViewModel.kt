package xyz.mercs.main.vm

import android.app.Application
import xyz.mercs.libcommon.base.mvvm.vm.BaseViewModel

class MainViewModel(app:Application):BaseViewModel(app) {
    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    fun init(){
        mTitle.set("密码")
    }
}