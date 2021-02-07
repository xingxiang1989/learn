package com.some.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author xiangxing
 */
class RetrofitUtil {
    companion object{
        /**
         * 创建Retrofit
         */
        fun create(url: String): Retrofit {
            //日志显示级别
//            val level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
//            //新建log拦截器
//            val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
//                message -> Logger.e("OkHttp: " + message)
//            })
//            loggingInterceptor.level = level
            // okHttpClientBuilder
            val okHttpClientBuilder = OkHttpClient().newBuilder()

            okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
            //OkHttp进行添加拦截器loggingInterceptor
            //okHttpClientBuilder.addInterceptor(loggingInterceptor)

            return Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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