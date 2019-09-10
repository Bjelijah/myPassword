package xyz.mercs.main.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import xyz.mercs.libcommon.base.mvvm.vm.BaseViewModel
import xyz.mercs.main.bean.FingerprintRes

class LoginViewModel(app:Application):BaseViewModel(app) {

    var mFingerRes: MutableLiveData<FingerprintRes>? = null
        get() {
            if(field==null){
                field = MutableLiveData()
            }
            return field
        }


    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    fun init(){}
}