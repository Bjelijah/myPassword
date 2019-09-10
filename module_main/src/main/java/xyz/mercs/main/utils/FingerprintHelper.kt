package xyz.mercs.main.utils

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal

object FingerprintHelper {


    private fun isFingerAvailable(fm:FingerprintManagerCompat?):Boolean{
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false
        }
        if (fm?.isHardwareDetected != true)return false
        if (!fm?.hasEnrolledFingerprints())return false
        return true
    }

    fun authenticate(c:Context):Boolean{

        val fm : FingerprintManagerCompat = FingerprintManagerCompat.from(c)
        if (!isFingerAvailable(fm))return false
        var cancelSignal = CancellationSignal()
        fm.authenticate(null,
            0,
            cancelSignal,
            object :FingerprintManagerCompat.AuthenticationCallback(){
                override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errMsgId, errString)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }

                override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                }


            },null)
        return true
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