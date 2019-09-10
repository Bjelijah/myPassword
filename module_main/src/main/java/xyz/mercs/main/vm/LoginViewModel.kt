package xyz.mercs.main.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import xyz.mercs.libcommon.base.mvvm.vm.BaseViewModel
import xyz.mercs.libcommon.utils.LOG
import xyz.mercs.main.bean.FingerprintRes
import xyz.mercs.main.utils.FingerprintHelper

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

    fun start(){
        FingerprintHelper.authenticate(mContext!!,object :FingerprintHelper.IFingerCallback{
            override fun onAuthenticated(id: Int) {
                LOG.I("123","onAuthenticated   id=$id")
                mFingerRes?.value = FingerprintRes(true,null)
            }

            override fun onFailed() {
                LOG.I("123","onAuthenticated")
            }

            override fun onHelp(helpCode: Int, str: CharSequence) {
                LOG.I("123","onAuthenticated   code=$helpCode   str=$str")
            }

            override fun onError(code: Int, s: CharSequence) {
                LOG.I("123","onError   code=$code  s=$s")
            }

            override fun onFingerCancel() {
                LOG.I("123","onFingerCancel")
            }

            override fun onFingerNoSupport() {
                LOG.I("123","onFingerNoSupport")
            }
        })
    }

    fun stop(){
        FingerprintHelper.cancelListener()
    }

}