package xyz.mercs.libnet

import android.content.Context
import android.util.Log
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.mercs.libnet.utils.SSLConection
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.UnrecoverableKeyException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit

class HttpManager private constructor(){
    companion object {
        val instance:HttpManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            HttpManager()
        }
    }




    private var mClient:OkHttpClient?=null
    private var mUploadClient:OkHttpClient?=null

    private var mUrl:String?=null
    private var mHttpApi:HttpApi?=null
    private var mUploadApi:HttpApi?=null
    private val mInterceptors = ArrayList<Interceptor>()
    private var mErrorCb:IErrorCallback ?= null

    fun addInterceptor(interceptor:Interceptor) : HttpManager{
        mInterceptors.add(interceptor)
        return this
    }

    fun error(e:()->Unit):HttpManager{
        mErrorCb = object :IErrorCallback{
            override fun onError() {
                e()
            }
        }
        return this
    }

    fun error(cb:IErrorCallback):HttpManager{
        mErrorCb = cb
        return this
    }

    fun initHttpClient(context: Context, url:String,isSSL:Boolean):HttpManager{
        try {
            if (mClient==null){
                val logInterceptor = HttpLoggingInterceptor{
                    Log.i("http",it)
                }
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val builder = if(isSSL){
                    SSLConection.getOKSSLBuild(context)
                }else{
                    OkHttpClient.Builder()
                }
                for (i in mInterceptors){
                    builder.addInterceptor(i)
                }
                mClient = builder
                        .retryOnConnectionFailure(true)
                        .addNetworkInterceptor(logInterceptor)
                        .connectTimeout(30,TimeUnit.SECONDS)
                        .readTimeout(30,TimeUnit.SECONDS)
                        .build()

                mUploadClient = builder
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(logInterceptor)
                    .connectTimeout(600,TimeUnit.SECONDS)
                    .readTimeout(600,TimeUnit.SECONDS)
                    .writeTimeout(600,TimeUnit.SECONDS)
                    .build()

                RxJavaPlugins.setErrorHandler{ t->
                    t.printStackTrace()
                    mErrorCb?.onError()
                }
                mUrl = url
            }
        }catch (e:KeyStoreException){
            e.printStackTrace()
        }catch (e:IOException){
            e.printStackTrace()
        }catch (e:CertificateException){
            e.printStackTrace()
        }catch (e:NoSuchAlgorithmException){
            e.printStackTrace()
        }catch (e:UnrecoverableKeyException){
            e.printStackTrace()
        }catch (e:KeyManagementException){
            e.printStackTrace()
        }
        mUrl = url
        return this
    }

    fun getHttpService():HttpApi{
        if (mHttpApi==null){
            val retrofit = Retrofit.Builder()
                    .client(mClient!!)
                    .baseUrl(mUrl!!)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            mHttpApi = retrofit.create(HttpApi::class.java)
        }
        return mHttpApi!!
    }

    fun getUploadService():HttpApi{
        if (mUploadApi==null){
            val retrofit = Retrofit.Builder()
                .client(mUploadClient!!)
                .baseUrl(mUrl!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mUploadApi = retrofit.create(HttpApi::class.java)
        }
        return mUploadApi!!
    }

    interface IErrorCallback{
        fun onError()
    }
}