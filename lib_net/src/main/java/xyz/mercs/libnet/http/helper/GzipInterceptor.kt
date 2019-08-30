package xyz.mercs.libnet.helper

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import okio.BufferedSink
import okio.GzipSink
import okio.Okio

class GzipInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (originalRequest.body()==null || originalRequest.header("Content-Encoding")!=null){
            return chain.proceed(originalRequest)
        }
        val compressedRequest = originalRequest.newBuilder()
                .header("Content-Encoding","gzip")
                .method(originalRequest.method(),gzip(originalRequest.body()!!))
                .build()
        return chain.proceed(compressedRequest)
    }
    private fun gzip(body:RequestBody):RequestBody{
        return object :RequestBody(){
            override fun contentType(): MediaType? = body.contentType()
            override fun contentLength(): Long =-1
            override fun writeTo(sink: BufferedSink) {
                var gzipSink = Okio.buffer(GzipSink(sink))
                body.writeTo(gzipSink)
                gzipSink.close()
            }
        }
    }
}