package xyz.mercs.libnet.utils

import android.content.Context
import okhttp3.OkHttpClient
import xyz.mercs.libnet.BuildConfig
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.*

object SSLConection {
    private val TAG = SSLConection::class.java.simpleName
    private var sTrustManagers:Array<TrustManager>?=null
    private var sSSLContext:SSLContext ?= null

    private fun contextSSL(context: Context):SSLContext{
        if (sTrustManagers==null) sTrustManagers = arrayOf(FakeX509TrustManager())
        var keyManagers:Array<KeyManager>?=null
        var ksIn :InputStream
        try{
            var keyStore = KeyStore.getInstance("BKS")

            ksIn = context.resources.assets.open(BuildConfig.SSL_CLIENT)
            keyStore.load(ksIn,BuildConfig.SSL_KEY.toCharArray())
            var kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            kmf.init(keyStore,BuildConfig.SSL_KEY.toCharArray())
            keyManagers = kmf.keyManagers
        }catch (e:Exception){}
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(keyManagers,sTrustManagers, SecureRandom())
        return sslContext
    }

    fun getOKSSLBuild(context:Context):OkHttpClient.Builder{
        if (sSSLContext==null) sSSLContext = contextSSL(context)
        return OkHttpClient.Builder()
                .sslSocketFactory(sSSLContext!!.socketFactory,FakeX509TrustManager())
                .hostnameVerifier { _, _ -> true }
    }

    fun allowSSL(context:Context){
        if (sSSLContext==null) sSSLContext = contextSSL(context)
        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true}
        HttpsURLConnection.setDefaultSSLSocketFactory(sSSLContext!!.socketFactory)
    }
}