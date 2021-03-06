package com.some.http

import android.util.Log
import com.some.http.converter.MyGsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author xiangxing
 * TODO: 单例
 *
 */
class RetrofitUtil {
    companion object{
        /**
         * 创建Retrofit
         */
        private fun create(url: String): Retrofit {
            //日志显示级别
            val level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
            //新建log拦截器
            val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                message -> Log.e("RetrofitUtil","OkHttp: " + message)
            })
            loggingInterceptor.level = level

            //将本地的证书放到本地
            //InputStream inputStream = context.getResources().openRawResource(R.raw.sever_vipbcw);

            val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    //设置ssl证书
//                    .sslSocketFactory(HttpsUtil.buildSSLSocketFactory(context, inputStream), X509TrustManagerImpl())
                    .build()

            return Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(MyGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        /**
         * 获取ServiceApi
         */
        fun <T> getService(url: String, service: Class<T>): T {
            return create(url).create(service)
        }

    }
}