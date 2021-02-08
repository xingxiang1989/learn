package com.some.mvvmdemo.http.request

import com.some.http.BaseEntity
import com.some.mvvmdemo.http.entity.EncryptInfo
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.POST

/**
 * 是会根据返回的类型进行选择callAdaper
 */
interface ApiService {

    @POST("/pub/config/getAppInfo")
    fun getConfig(): Observable<BaseEntity<EncryptInfo>>

    @POST("/pub/config/getAppInfo")
    fun getConfig2(): Call<BaseEntity<EncryptInfo>>
}