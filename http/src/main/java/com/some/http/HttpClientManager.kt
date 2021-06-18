package com.some.http

import android.content.Context
import android.util.Log
import android.util.SparseArray
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by xiangxing5 on 2021/6/17.
 * Describe:
 */
class HttpClientManager {



    companion object{

        //可以根据type进行缓存okhttpclient
        val httpClientMap = SparseArray<OkHttpClient>()

        fun getClient(context: Context, type: Int):OkHttpClient{

            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Log.e("OkHttp", "OkHttp: $message")
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            //将本地的证书放到本地
//            val inputStream = context.getResources().openRawResource(R.raw.sever_vipbcw)
//            val sslParams = HttpsUtils.getSslSocketFactory(inputStream)
            val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    //设置ssl证书
//                    .sslSocketFactory(HttpsUtil.buildSSLSocketFactory(context, inputStream), X509TrustManagerImpl())
                    .build()
            return okHttpClient
        }

        fun clearCache(){
            httpClientMap.clear()
        }
    }


}