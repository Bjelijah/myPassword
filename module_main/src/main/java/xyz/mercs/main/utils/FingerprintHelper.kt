package xyz.mercs.main.utils

import android.content.Context
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal
import xyz.mercs.libcommon.utils.LOG

object FingerprintHelper {

    var mCancelSignal:CancellationSignal?=null

    private fun isFingerAvailable(fm:FingerprintManagerCompat?):Boolean{
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false
        }
        if (fm?.isHardwareDetected != true){
            LOG.E("123","hard not detected")
            return false
        }
        if (!fm?.hasEnrolledFingerprints()){
            LOG.E("123","no fingerprints")
            return false
        }
        return true
    }

    fun authenticate(c:Context,cb:IFingerCallback){

        val fm : FingerprintManagerCompat = FingerprintManagerCompat.from(c)
        if (!isFingerAvailable(fm)){
            cb.onFingerNoSupport()
            return
        }
        mCancelSignal = CancellationSignal()
        fm.authenticate(null,
            0,
            mCancelSignal,
            object :FingerprintManagerCompat.AuthenticationCallback(){
                override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errMsgId, errString)
                    cb.onError(errMsgId,errString?:"")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    cb.onFailed()
                }

                override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    LOG.I("123","successed  result=${result?.cryptoObject}")
                    cb.onAuthenticated(0)
                }

                override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                    super.onAuthenticationHelp(helpMsgId, helpString)
                    cb.onHelp(helpMsgId,helpString?:"")
                }


            },null)
        return
    }

    fun cancelListener(){
        mCancelSignal?.cancel()
    }

    private fun getFingerprint(result: FingerprintManagerCompat.AuthenticationResult){
        var c:Class<FingerprintManagerCompat.AuthenticationResult>  = FingerprintManagerCompat.AuthenticationResult::class.java
        var method1 = c.getMethod("getFingerprint")
        method1.isAccessible = true
        var o = method1.invoke(result)
        LOG.I("123","o name=${o.javaClass.name}")
        var className = o.javaClass.name
        var fingerprint = Class.forName(className)
        var method2 = fingerprint.getMethod("getFingerId")
        var idObj = method2.invoke(o)
        var fingerID = idObj.toString().toInt()
        var method3 = fingerprint.getMethod("getName")
        var nameObj = method3.invoke(o)
        var name = nameObj.toString()
        LOG.I("123","fingerID=$fingerID   name=$name")
    }



    interface IFingerCallback {
        fun onAuthenticated(id: Int)
        fun onFailed()
        fun onHelp(helpCode: Int, str: CharSequence)
        fun onError(code: Int, s: CharSequence)
        fun onFingerCancel()
        fun onFingerNoSupport()
    }
}