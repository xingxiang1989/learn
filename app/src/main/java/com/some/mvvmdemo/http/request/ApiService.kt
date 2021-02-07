package com.some.mvvmdemo.http.request

import com.some.http.BaseEntity
import com.some.mvvmdemo.http.entity.EncryptInfo
import io.reactivex.Observable
import retrofit2.http.POST

interface ApiService {

    @POST("/pub/config/getAppInfo")
    fun getConfig(): Observable<BaseEntity<EncryptInfo>>
}